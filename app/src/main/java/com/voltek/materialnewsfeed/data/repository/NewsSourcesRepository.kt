package com.voltek.materialnewsfeed.data.repository

import android.content.Context
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.networking.NewsApi
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.interactor.Result
import com.voltek.materialnewsfeed.utils.NetworkUtils
import io.reactivex.Observable
import javax.inject.Inject

class NewsSourcesRepository : DataProvider.NewsSources {

    // TODO hide access to realm under DB facade

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mContext: Context

    init {
        NewsApp.dataComponent.inject(this)
    }

    override fun getAll(): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        val sourcesCache = Source().queryAll()

        if (sourcesCache.isEmpty()) {
            try {
                NetworkUtils.checkConnection(mContext)

                val call = mApi.fetchSources(BuildConfig.ApiKey).execute()
                if (call.isSuccessful) {
                    call.body().sources.saveAll()
                    emitter.onNext(Result(Source().queryAll()))
                } else {
                    emitter.onNext(Result(null, mContext.getString(R.string.error_request_failed)))
                }
            } catch (e: Exception) {
                emitter.onNext(Result(null, mContext.getString(R.string.error_no_connection)))
            }
        } else {
            emitter.onNext(Result(sourcesCache))
        }

        emitter.onComplete()
    }

    override fun getCategory(category: String): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        val query: List<Source>
        var message = ""

        if (category == mContext.getString(R.string.category_all)) {
            query = Source().queryAll()

            if (query.isEmpty())
                message = mContext.getString(R.string.error_no_news_sources_loaded)
        } else if (category == mContext.getString(R.string.category_enabled) || category.isEmpty()) {
            query = Source().query({ query -> query.equalTo("isEnabled", true) })

            if (query.isEmpty())
                message = mContext.getString(R.string.error_no_news_sources_selected_yet)
        } else {
            query = Source().query({ query -> query.equalTo("category", category.toLowerCase()) })

            if (query.isEmpty())
                message = mContext.getString(R.string.error_no_news_sources_for_category)
        }

        emitter.onNext(Result(query, message))
        emitter.onComplete()
    }

    override fun update(): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it
        try {
            NetworkUtils.checkConnection(mContext)

            val call = mApi.fetchSources(BuildConfig.ApiKey).execute()
            if (call.isSuccessful) {
                val current = Source().query({ query -> query.equalTo("isEnabled", true) })
                val new = call.body().sources as ArrayList

                for (source in new)
                    for (enabled in current)
                        if (source.id == enabled.id)
                            source.isEnabled = true

                Source().deleteAll()
                new.saveAll()
                emitter.onNext(Result(Source().queryAll()))
            } else {
                emitter.onNext(Result(null, mContext.getString(R.string.error_request_failed)))
            }
        } catch (e: Exception) {
            emitter.onNext(Result(Source().queryAll(), mContext.getString(R.string.error_no_connection)))
        }
        emitter.onComplete()
    }
}
