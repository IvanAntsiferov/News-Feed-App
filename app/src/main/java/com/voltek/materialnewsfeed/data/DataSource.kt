package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.api.NewsApiArticlesResponse
import io.reactivex.Observable

object DataSource {

    interface Articles {

        fun provideArticles(source: String): Observable<NewsApiArticlesResponse>
    }
}
