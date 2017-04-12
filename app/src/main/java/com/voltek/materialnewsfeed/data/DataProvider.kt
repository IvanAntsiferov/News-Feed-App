package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.api.NewsApiArticlesResponse
import com.voltek.materialnewsfeed.data.api.NewsApiSourcesResponse
import io.reactivex.Observable

object DataProvider {

    interface Articles {

        fun provideArticles(source: String): Observable<NewsApiArticlesResponse>
    }

    interface NewsSources {

        fun provideNewsSources(): Observable<NewsApiSourcesResponse>
    }
}
