package com.voltek.materialnewsfeed.domain.repository

import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.domain.interactor.Result
import io.reactivex.Observable

class NewsSourcesRepository {

    @javax.inject.Inject
    lateinit var mContext: android.content.Context

    @javax.inject.Inject
    lateinit var mApi: com.voltek.materialnewsfeed.data.networking.NewsApi

    init {
       NewsApp.repositoryComponent.inject(this)
    }

    fun getAll(): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        val sourcesCache = Source().queryAll()

        if (sourcesCache.isEmpty()) {
            try {
                com.voltek.materialnewsfeed.utils.NetworkUtils.checkConnection(mContext)

                val call = mApi.fetchSources(com.voltek.materialnewsfeed.BuildConfig.ApiKey).execute()
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

    fun getCategory(category: String): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        val query: List<Source>
        var message = ""

        if (category == mContext.getString(com.voltek.materialnewsfeed.R.string.category_all)) {
            query = Source().queryAll()

            if (query.isEmpty())
                message = mContext.getString(com.voltek.materialnewsfeed.R.string.error_no_news_sources_loaded)
        } else if (category == mContext.getString(com.voltek.materialnewsfeed.R.string.category_enabled) || category.isEmpty()) {
            query = Source().query({ query -> query.equalTo("isEnabled", true) })

            if (query.isEmpty())
                message = mContext.getString(com.voltek.materialnewsfeed.R.string.error_no_news_sources_selected_yet)
        } else {
            query = Source().query({ query -> query.equalTo("category", category.toLowerCase()) })

            if (query.isEmpty())
                message = mContext.getString(com.voltek.materialnewsfeed.R.string.error_no_news_sources_for_category)
        }

        emitter.onNext(Result(query, message))
        emitter.onComplete()
    }

    fun refresh(): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it
        try {
            com.voltek.materialnewsfeed.utils.NetworkUtils.checkConnection(mContext)

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
