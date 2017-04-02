package com.voltek.materialnewsfeed.mvp

import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.api.NewsApi
import com.voltek.materialnewsfeed.api.NewsApiArticlesResponse
import io.reactivex.Observable
import javax.inject.Inject

class NewsModel {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    fun provideArticles(source: String): Observable<NewsApiArticlesResponse> {
        return mApi.fetchArticles(BuildConfig.ApiKey, source)
    }
}
