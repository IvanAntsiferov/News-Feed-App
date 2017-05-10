package com.voltek.newsfeed.data.db

import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.entity.Source

class DatabaseDelegate : Provider.Database.NewsSources {

    init {
        NewsApp.dataComponent.inject(this)
    }

    // Provider.Database.NewsSources
    override fun queryAll(): List<Source> = Source().queryAll()

    override fun queryEnabled(): List<Source> =
            Source().query({ query -> query.equalTo("isEnabled", true) })

    override fun queryCategory(category: String): List<Source> =
            Source().query({ query -> query.equalTo("category", category.toLowerCase()) })


    override fun save(items: List<Source>) {
        items.saveAll()
    }

    override fun deleteAll() {
        Source().deleteAll()
    }
}
