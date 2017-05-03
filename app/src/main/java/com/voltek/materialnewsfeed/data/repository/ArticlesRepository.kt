package com.voltek.materialnewsfeed.data.repository

import android.content.Context
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.utils.RepositoryUtils
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.networking.NewsApi
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.exception.NoNewsSourcesException
import com.voltek.materialnewsfeed.data.exception.RequestFailedException
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesRepository : DataProvider.Articles {

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mSourcesRepo: DataProvider.NewsSources

    init {
        NewsApp.dataComponent.inject(this)
    }

    override fun get(): Observable<List<Article>> = Observable.create {
        RepositoryUtils.checkConnection(mContext)
        val emitter = it

        val sources = mSourcesRepo.getCategory(mContext.getString(R.string.category_enabled))

        if (!sources.isEmpty()) {
            for (source in sources) {
                val call = mApi.fetchArticles(BuildConfig.ApiKey, source.id).execute()
                if (call.isSuccessful) {
                    emitter.onNext(call.body().articles)
                } else {
                    emitter.onError(
                            RequestFailedException(mContext.getString(R.string.error_request_failed))
                    )
                }
            }
        } else {
            emitter.onError(
                    NoNewsSourcesException(mContext.getString(R.string.error_no_news_sources))
            )
        }

        emitter.onComplete()
    }
}
