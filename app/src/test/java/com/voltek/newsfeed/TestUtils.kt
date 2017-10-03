package com.voltek.newsfeed

import com.voltek.newsfeed.data.network.entity.ArticleAPI
import com.voltek.newsfeed.data.network.entity.SourceAPI
import com.voltek.newsfeed.data.storage.entity.SourceDB
import com.voltek.newsfeed.presentation.entity.SourceUI

object TestUtils {

    const val stringNoConnection = "stringNoConnection"
    const val stringServerError = "stringServerError"

    fun sourceAPI() = SourceAPI(
            id = "id",
            name = "name",
            description = "description",
            url = "url",
            category = "category",
            country = "country",
            isEnabled = false)

    fun articleAPI() = ArticleAPI(
            title = "title",
            description = "description",
            url = "url",
            urlToImage = "urlToImage")

    fun sourceDB(): SourceDB {
        val s = SourceDB()
        with(s) {
            id = "id"
            name = "name"
            description = "description"
            url = "url"
            category = "category"
            country = "country"
            isEnabled = false
        }
        return s
    }

    fun sourceUI() = SourceUI(
            "id",
            "name",
            "description",
            "url",
            "category",
            "country",
            false)
}
