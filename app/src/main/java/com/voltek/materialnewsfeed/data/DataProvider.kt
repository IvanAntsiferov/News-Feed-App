package com.voltek.materialnewsfeed.data

import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.interactor.Result
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Holds set of interfaces that provides access to data.
 */
object DataProvider {

    interface Articles {

        fun get(sources: List<Source>): Observable<Result<List<Article>?>>
    }

    interface NewsSources {

        fun getAll(): Observable<Result<List<Source>?>>

        fun getCategory(category: String): Observable<Result<List<Source>?>>

        fun deleteAll(): Single<Boolean>
    }
}
