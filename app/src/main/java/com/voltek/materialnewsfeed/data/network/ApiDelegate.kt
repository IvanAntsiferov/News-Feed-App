package com.voltek.materialnewsfeed.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.Provider
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.data.exception.NoConnectionException
import com.voltek.materialnewsfeed.data.exception.RequestFailedException
import com.voltek.materialnewsfeed.data.network.api.NewsApi
import io.reactivex.Single
import javax.inject.Inject

class ApiDelegate : Provider.Api.Articles, Provider.Api.NewsSources {

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mApi: NewsApi

    init {
        NewsApp.dataComponent.inject(this)
    }

    override fun get(source: String): Single<List<Article>> = Single.create {
        val emitter = it

        if (hasConnection()) {
            val call = mApi.fetchArticles(BuildConfig.ApiKey, source).execute()
            if (call.isSuccessful) {
                emitter.onSuccess(call.body().articles)
            } else {
                emitter.onError(RequestFailedException())
            }
        } else {
            emitter.onError(NoConnectionException())
        }
    }

    override fun get(): Single<List<Source>> = Single.create {
        val emitter = it

        if (hasConnection()) {
            val call = mApi.fetchSources(BuildConfig.ApiKey).execute()
            if (call.isSuccessful) {
                emitter.onSuccess(call.body().sources)
            } else {
                emitter.onError(RequestFailedException())
            }
        } else {
            emitter.onError(NoConnectionException())
        }
    }

    private fun hasConnection(): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
