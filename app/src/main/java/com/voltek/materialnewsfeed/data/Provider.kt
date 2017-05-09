package com.voltek.materialnewsfeed.data

import android.support.annotation.IdRes
import com.voltek.materialnewsfeed.data.entity.Source

/**
 * Set of interfaces, that provides access to data layer
 */
object Provider {

    object Api {

        interface Articles {}

        interface NewsSources {}
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

            fun getString(@IdRes id: Int, vararg args: Any): String
        }
    }
}
