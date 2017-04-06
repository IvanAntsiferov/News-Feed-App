package com.voltek.materialnewsfeed.mvp

import com.voltek.materialnewsfeed.data.api.NewsApiArticlesResponse
import io.reactivex.Observable

object ModelContract {

    interface Articles {

        fun provideArticles(source: String): Observable<NewsApiArticlesResponse>
    }
}
