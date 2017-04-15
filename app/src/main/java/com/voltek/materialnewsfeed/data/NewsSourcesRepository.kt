package com.voltek.materialnewsfeed.data

import android.content.Context
import android.net.ConnectivityManager
import android.os.NetworkOnMainThreadException
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.api.NewsApi
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.Observable
import javax.inject.Inject

class NewsSourcesRepository : DataProvider.NewsSources {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mContext: Context

    override fun provideNewsSources(): Observable<List<Source>> = Observable.create {
        val emitter = it

        val sourcesCache = Source().queryAll()

        if (sourcesCache.isEmpty()) {
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                val observable = mApi.fetchSources(BuildConfig.ApiKey)
                observable.subscribe({
                    it.sources.saveAll()
                    emitter.onNext(Source().queryAll())
                }, {
                    emitter.onError(it)
                })
            } else {
                emitter.onError(NetworkOnMainThreadException())
            }
        } else {
            emitter.onNext(sourcesCache)
        }
    }

    override fun provideEnabledSources(): List<Source> =
            Source().query { query -> query.equalTo("isEnabled", true) }
}
