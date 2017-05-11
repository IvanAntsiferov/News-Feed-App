package com.voltek.newsfeed.data

import android.support.annotation.IdRes
import com.voltek.newsfeed.data.entity.Article
import com.voltek.newsfeed.data.entity.Source
import io.reactivex.Single

/**
 * Set of interfaces, that provides access to data layer
 */
object Provider {

    object Api {

        interface Articles {
            fun get(source: String): Single<List<Article>>
        }

        interface NewsSources {
            fun get(): Single<List<Source>>
        }
    }

    object Database {

        interface NewsSources {

            fun queryAll(): List<Source>

            fun queryEnabled(): List<Source>

            fun queryCategory(category: String): List<Source>

            fun save(items: List<Source>)

            fun deleteAll()
        }
    }

    object Platform {

        interface Resources {

            fun getString(@IdRes id: Int): String
        }
    }
}
