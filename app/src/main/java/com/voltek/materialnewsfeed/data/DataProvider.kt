package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.Observable

object DataProvider {

    interface Articles {

        fun provideArticles(): Observable<List<Article>>
    }

    interface NewsSources {

        fun provideNewsSources(): Observable<List<Source>>

        fun provideEnabledSources(): List<Source>
    }
}
