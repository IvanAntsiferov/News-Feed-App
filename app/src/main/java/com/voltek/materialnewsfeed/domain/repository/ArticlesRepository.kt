package com.voltek.materialnewsfeed.domain.repository

import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.Provider
import com.voltek.materialnewsfeed.utils.NetworkUtils
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.data.network.api.NewsApi
import com.voltek.materialnewsfeed.domain.interactor.Result
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesRepository {

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mRes: Provider.Platform.Resources

    init {
        NewsApp.repositoryComponent.inject(this)
    }

    fun get(sources: List<Source>): Observable<Result<List<Article>?>> = Observable.create {
        NetworkUtils.checkConnection(mContext)
        val emitter = it

        if (!sources.isEmpty()) {
            for (source in sources) {
                val call = mApi.fetchArticles(BuildConfig.ApiKey, source.id).execute()
                if (call.isSuccessful) {
                    val result = ArrayList<Article>()
                    val sourceTitle = Article()
                    sourceTitle.source = source.name
                    result.add(sourceTitle)
                    result.addAll(call.body().articles)
                    emitter.onNext(Result(result))
                } else {
                    emitter.onNext(
                            Result(
                                    null,
                                    mRes.getString(R.string.error_retrieve_failed, source.name)
                            )
                    )
                }
            }
        } else {
            emitter.onError(Exception(mRes.getString(R.string.error_no_news_sources_selected)))
        }

        emitter.onComplete()
    }
}
