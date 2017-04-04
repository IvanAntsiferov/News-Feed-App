package com.voltek.materialnewsfeed.ui.list

import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.api.NewsApi
import com.voltek.materialnewsfeed.api.NewsApiArticlesResponse
import com.voltek.materialnewsfeed.ui.list.ListContract
import io.reactivex.Observable
import javax.inject.Inject

class ArticleModel : ListContract.Model {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    override fun provideArticles(source: String): Observable<NewsApiArticlesResponse> {
        return mApi.fetchArticles(BuildConfig.ApiKey, source)
    }
}
