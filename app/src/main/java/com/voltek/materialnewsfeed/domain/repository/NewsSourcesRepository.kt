package com.voltek.materialnewsfeed.domain.repository

import android.content.Context
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.Provider
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.data.network.api.NewsApi
import com.voltek.materialnewsfeed.domain.interactor.Result
import com.voltek.materialnewsfeed.utils.NetworkUtils
import io.reactivex.Observable
import javax.inject.Inject

class NewsSourcesRepository {

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mDb: Provider.Database.NewsSources

    init {
       NewsApp.repositoryComponent.inject(this)
    }

    fun getAll(): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        val sourcesCache = mDb.queryAll()

        if (sourcesCache.isEmpty()) {
            try {
                NetworkUtils.checkConnection(mContext)

                val call = mApi.fetchSources(BuildConfig.ApiKey).execute()
                if (call.isSuccessful) {
                    mDb.save(call.body().sources)
                    emitter.onNext(Result(mDb.queryAll()))
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
            query = mDb.queryAll()

            if (query.isEmpty())
                message = mContext.getString(R.string.error_no_news_sources_loaded)
        } else if (category == mContext.getString(com.voltek.materialnewsfeed.R.string.category_enabled) || category.isEmpty()) {
            query = mDb.queryEnabled()

            if (query.isEmpty())
                message = mContext.getString(R.string.error_no_news_sources_selected_yet)
        } else {
            query = mDb.queryCategory(category.toLowerCase())

            if (query.isEmpty())
                message = mContext.getString(R.string.error_no_news_sources_for_category)
        }

        emitter.onNext(Result(query, message))
        emitter.onComplete()
    }

    fun refresh(): Observable<Result<List<Source>?>> = Observable.create {
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
