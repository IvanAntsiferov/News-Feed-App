package com.voltek.materialnewsfeed.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.api.NewsApi
import com.voltek.materialnewsfeed.data.api.Article
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class ArticlesRepository : DataProvider.Articles {

    init {
        NewsApp.MainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mSourcesRepo: DataProvider.NewsSources

    override fun getAll(): Observable<List<Article>> = Observable.create {
        val emitter = it

        val sources = mSourcesRepo.getEnabled()

        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        // If there is some news sources enabled and device has internet connection
        if (!sources.isEmpty()) {
            if (networkInfo != null && networkInfo.isConnected) {
                for (source in sources) {
                    val call = mApi.fetchArticles(BuildConfig.ApiKey, source.id).execute()
                    if (call.isSuccessful) {
                        emitter.onNext(call.body().articles)
                    } else {
                        //call.errorBody()
                        emitter.onError(Exception())
                    }
                }
            } else {
                // No internet connection
                emitter.onError(Exception())
            }
        } else {
            // There is no news sources chosen
            emitter.onError(Exception())
        }
        emitter.onComplete()
    }
}
