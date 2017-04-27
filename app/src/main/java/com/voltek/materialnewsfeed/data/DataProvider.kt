package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.networking.Article
import com.voltek.materialnewsfeed.data.networking.Source
import io.reactivex.Observable

/**
 * Holds set of interfaces that provides access to data.
 */
object DataProvider {

    interface Articles {

        fun getAll(): Observable<List<Article>>
    }

    interface NewsSources {

        fun getAll(): Observable<List<Source>>

        fun getEnabled(): List<Source>
    }
}
