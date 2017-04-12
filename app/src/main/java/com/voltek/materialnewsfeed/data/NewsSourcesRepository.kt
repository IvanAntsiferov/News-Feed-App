package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.api.NewsApi
import com.voltek.materialnewsfeed.data.api.NewsApiSourcesResponse
import io.reactivex.Observable
import javax.inject.Inject

class NewsSourcesRepository : DataProvider.NewsSources {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    override fun provideNewsSources(): Observable<NewsApiSourcesResponse> {
        return mApi.fetchSources(BuildConfig.ApiKey)
    }
}
