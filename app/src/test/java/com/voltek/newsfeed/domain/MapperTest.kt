package com.voltek.newsfeed.domain

import com.voltek.newsfeed.data.network.entity.ArticleAPI
import com.voltek.newsfeed.data.network.entity.SourceAPI
import com.voltek.newsfeed.data.storage.entity.SourceDB
import junit.framework.Assert.assertEquals
import org.junit.Test

class MapperTest {

    private val sourceAPI = SourceAPI(
            id = "id",
            name = "name",
            description = "description",
            url = "url",
            category = "category",
            country = "country",
            isEnabled = false)

    private val sourceDB: SourceDB
        get() {
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

    private val articleAPI = ArticleAPI(
            title = "title",
            description = "description",
            url = "url",
            urlToImage = "urlToImage")

    @Test
    fun articleAPItoUI() {
        val articleUI = Mapper.articleAPItoUI(articleAPI)
        assertEquals(articleAPI.title, articleUI.title)
        assertEquals(articleAPI.description, articleUI.description)
        assertEquals(articleAPI.url, articleUI.url)
        assertEquals(articleAPI.urlToImage, articleUI.urlToImage)
    }

    @Test
    fun sourceAPItoDB() {
        val sourceDB = Mapper.sourceAPItoDB(sourceAPI)
        assertEquals(sourceAPI.id, sourceDB.id)
        assertEquals(sourceAPI.name, sourceDB.name)
        assertEquals(sourceAPI.description, sourceDB.description)
        assertEquals(sourceAPI.url, sourceDB.url)
        assertEquals(sourceAPI.category, sourceDB.category)
        assertEquals(sourceAPI.country, sourceDB.country)
        assertEquals(sourceAPI.isEnabled, sourceDB.isEnabled)
    }

    @Test
    fun sourceDBtoUI() {
        val sourceUI = Mapper.sourceDBtoUI(sourceDB)
        assertEquals(sourceDB.id, sourceUI.id)
        assertEquals(sourceDB.name, sourceUI.name)
        assertEquals(sourceDB.description, sourceUI.description)
        assertEquals(sourceDB.url, sourceUI.url)
        assertEquals(sourceDB.category, sourceUI.category)
        assertEquals(sourceDB.country, sourceUI.country)
        assertEquals(sourceDB.isEnabled, sourceUI.isEnabled)
    }
}
