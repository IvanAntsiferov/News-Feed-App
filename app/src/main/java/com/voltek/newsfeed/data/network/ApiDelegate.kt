package com.voltek.newsfeed.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.voltek.newsfeed.BuildConfig
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.entity.ArticleRAW
import com.voltek.newsfeed.data.entity.SourceRAW
import com.voltek.newsfeed.data.exception.NoConnectionException
import com.voltek.newsfeed.data.exception.RequestFailedException
import com.voltek.newsfeed.data.network.api.NewsApi
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

    override fun get(source: String): Single<List<ArticleRAW>> = Single.create {
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

    override fun get(): Single<List<SourceRAW>> = Single.create {
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
