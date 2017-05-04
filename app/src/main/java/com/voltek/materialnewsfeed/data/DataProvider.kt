package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.interactor.articles.ArticlesResult
import io.reactivex.Observable

/**
 * Holds set of interfaces that provides access to data.
 */
object DataProvider {

    interface Articles {

        fun get(): Observable<ArticlesResult>
    }

    interface NewsSources {

        fun getAll(): Observable<List<Source>>

        fun getCategory(category: String): List<Source>
    }
}
