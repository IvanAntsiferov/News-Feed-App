package com.voltek.newsfeed.data

import android.support.annotation.IdRes
import com.voltek.newsfeed.data.entity.ArticleRAW
import com.voltek.newsfeed.data.entity.SourceRAW
import io.reactivex.Single

/**
 * Set of interfaces, that provides access to data layer
 */
object Provider {

    object Api {

        interface Articles {
            fun get(source: String): Single<List<ArticleRAW>>
        }

        interface NewsSources {
            fun get(): Single<List<SourceRAW>>
        }
    }

    object Database {

        interface NewsSources {

            fun queryAll(): List<SourceRAW>

            fun queryEnabled(): List<SourceRAW>

            fun queryCategory(category: String): List<SourceRAW>

            fun findById(id: String): SourceRAW?

            fun save(items: List<SourceRAW>)

            fun deleteAll()
        }
    }

    object Platform {

        interface Resources {

            fun getString(@IdRes id: Int): String
        }
    }
}
