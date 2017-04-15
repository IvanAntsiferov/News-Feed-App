package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.api.NewsApi
import com.voltek.materialnewsfeed.data.api.NewsApiArticlesResponse
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.list.ListContract
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesRepository : DataProvider.Articles {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mApi: NewsApi

    private val mSourcesRepo: DataProvider.NewsSources = NewsSourcesRepository()

    override fun provideArticles(): Observable<List<Article>> = Observable.create {
        val emitter = it

        val sources = mSourcesRepo.provideEnabledSources()

        if (!sources.isEmpty()) {
            for (source in sources) {
                val call = mApi.fetchArticles(BuildConfig.ApiKey, source.id)
                val articles = call.execute().body().articles
                emitter.onNext(articles)
            }
        } else {
            // There is no news sources chosen
            emitter.onError(NullPointerException())
        }
    }
}
