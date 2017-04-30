package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source
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

        fun getCategory(category: String): List<Source>
    }
}
