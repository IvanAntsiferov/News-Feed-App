package com.voltek.materialnewsfeed.data.repository

import android.content.Context
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

        if (category == mContext.getString(R.string.category_all)) {
            emitter.onNext(Result(Source().queryAll()))
        } else if (category == mContext.getString(R.string.category_enabled) || category.isEmpty()) {
            emitter.onNext(Result(Source().query({ query -> query.equalTo("isEnabled", true) })))
        } else {
            emitter.onNext(Result(Source().query({ query -> query.equalTo("category", category) })))
        }

        emitter.onComplete()
    }
}
