package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.Observable

object DataProvider {

    interface Articles {

        fun getAll(): Observable<List<Article>>
    }

    interface NewsSources {

        fun getAll(): Observable<List<Source>>

        fun getEnabled(): List<Source>
    }
}
