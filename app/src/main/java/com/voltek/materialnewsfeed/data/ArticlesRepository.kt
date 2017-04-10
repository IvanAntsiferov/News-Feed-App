package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.api.NewsApi
import com.voltek.materialnewsfeed.data.api.NewsApiArticlesResponse
import com.voltek.materialnewsfeed.data.DataSource
import com.voltek.materialnewsfeed.ui.list.ListContract
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesRepository : DataSource.Articles {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    override fun provideArticles(source: String): Observable<NewsApiArticlesResponse> {
        return mApi.fetchArticles(BuildConfig.ApiKey, source)
    }
}
