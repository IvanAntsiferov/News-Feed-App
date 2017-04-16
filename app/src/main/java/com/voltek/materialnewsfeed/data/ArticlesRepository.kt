package com.voltek.materialnewsfeed.data

import android.os.NetworkOnMainThreadException
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.api.NewsApi
import com.voltek.materialnewsfeed.data.api.Article
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
                val call = mApi.fetchArticles(BuildConfig.ApiKey, source.id).execute()
                if (call.isSuccessful) {
                    emitter.onNext(call.body().articles)
                } else {
                    //call.errorBody()
                    emitter.onError(NetworkOnMainThreadException())
                }
            }
        } else {
            // There is no news sources chosen
            emitter.onError(NullPointerException())
        }
        emitter.onComplete()
    }
}
