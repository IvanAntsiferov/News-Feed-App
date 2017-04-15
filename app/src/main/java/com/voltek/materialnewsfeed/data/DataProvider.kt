package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.api.NewsApiArticlesResponse
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.Observable

object DataProvider {

    interface Articles {

        fun provideArticles(): Observable<NewsApiArticlesResponse>
    }

    interface NewsSources {

        fun provideNewsSources(): Observable<List<Source>>
    }
}
