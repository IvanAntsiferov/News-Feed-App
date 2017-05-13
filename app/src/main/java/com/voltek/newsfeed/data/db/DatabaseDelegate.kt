package com.voltek.newsfeed.data.db

import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.entity.SourceRAW

class DatabaseDelegate : Provider.Database.NewsSources {

    init {
        NewsApp.dataComponent.inject(this)
    }

    // Provider.Database.NewsSources
    override fun queryAll(): List<SourceRAW> = SourceRAW().queryAll()

    override fun queryEnabled(): List<SourceRAW> =
            SourceRAW().query({ query -> query.equalTo("isEnabled", true) })

    override fun queryCategory(category: String): List<SourceRAW> =
            SourceRAW().query({ query -> query.equalTo("category", category.toLowerCase()) })


    override fun save(items: List<SourceRAW>) {
        items.saveAll()
    }

    override fun deleteAll() {
        SourceRAW().deleteAll()
    }
}
