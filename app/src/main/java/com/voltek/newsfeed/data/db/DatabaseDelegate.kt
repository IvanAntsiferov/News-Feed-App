package com.voltek.newsfeed.data.db

import com.vicpin.krealmextensions.*
import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.entity.SourceRAW

class DatabaseDelegate : Provider.Database.NewsSources {

    // Provider.Database.NewsSources
    override fun queryAll(): List<SourceRAW> = SourceRAW().queryAll()

    override fun queryEnabled(): List<SourceRAW> =
            SourceRAW().query { query -> query.equalTo("isEnabled", true) }

    override fun queryCategory(category: String): List<SourceRAW> =
            SourceRAW().query { query -> query.equalTo("category", category.toLowerCase()) }

    override fun findById(id: String): SourceRAW? =
            SourceRAW().queryFirst { query -> query.equalTo("id", id) }

    override fun save(items: List<SourceRAW>) {
        items.saveAll()
    }

    override fun deleteAll() {
        SourceRAW().deleteAll()
    }
}
