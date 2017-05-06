package com.voltek.materialnewsfeed.data.repository

import android.content.Context
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.utils.NetworkUtils
import com.voltek.materialnewsfeed.data.Repository
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.data.networking.NewsApi
import com.voltek.materialnewsfeed.interactor.Result
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesRepository : Repository.Articles {

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mApi: NewsApi

    init {
        NewsApp.repositoryComponent.inject(this)
    }

    override fun get(sources: List<Source>): Observable<Result<List<Article>?>> = Observable.create {
        NetworkUtils.checkConnection(mContext)
        val emitter = it

        if (!sources.isEmpty()) {
            for (source in sources) {
                val call = mApi.fetchArticles(BuildConfig.ApiKey, source.id).execute()
                if (call.isSuccessful) {
                    emitter.onNext(Result(call.body().articles))
                } else {
                    emitter.onNext(
                            Result(
                                    null,
                                    mContext.getString(R.string.error_retrieve_failed, source.name)
                            )
                    )
                }
            }
        } else {
            emitter.onError(Exception(mContext.getString(R.string.error_no_news_sources_selected)))
        }

        emitter.onComplete()
    }
}
