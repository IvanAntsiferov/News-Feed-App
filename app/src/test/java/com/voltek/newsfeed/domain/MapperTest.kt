package com.voltek.newsfeed.domain

import com.voltek.newsfeed.MockData
import junit.framework.Assert.assertEquals
import org.junit.Test

class MapperTest {

    private val sourceAPI = MockData.sourceAPI()

    private val sourceDB = MockData.sourceDB()

    private val articleAPI = MockData.articleAPI()

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
