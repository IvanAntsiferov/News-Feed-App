package com.voltek.materialnewsfeed.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.os.NetworkOnMainThreadException
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.api.NewsApi
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.Observable
import javax.inject.Inject

class NewsSourcesRepository : DataProvider.NewsSources {

    init {
        MaterialNewsFeedApp.MainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mContext: Context

    override fun getAll(): Observable<List<Source>> = Observable.create {
        val emitter = it

        val sourcesCache = Source().queryAll()

        if (sourcesCache.isEmpty()) {
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                val call = mApi.fetchSources(BuildConfig.ApiKey).execute()
                if (call.isSuccessful) {
                    call.body().sources.saveAll()
                    emitter.onNext(Source().queryAll())
                } else {
                    //call.errorBody()
                    emitter.onError(NetworkOnMainThreadException())
                }
            } else {
                // No internet connection
                emitter.onError(NetworkOnMainThreadException())
            }
        } else {
            emitter.onNext(sourcesCache)
        }
        emitter.onComplete()
    }

    override fun getEnabled(): List<Source> =
            Source().query { query -> query.equalTo("isEnabled", true) }
}
