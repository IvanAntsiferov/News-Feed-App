package com.voltek.newsfeed.data.network

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.nio.charset.Charset

class NewsApiTest {

    private lateinit var service: NewsApi
    private lateinit var mockServer: MockWebServer

    @Before
    fun prepare() {
        mockServer = MockWebServer()
        service = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mockServer.url("/"))
                .build()
                .create(NewsApi::class.java)
    }

    @After
    fun clear() {
        mockServer.shutdown()
    }

    @Test
    fun getArticles() {
        enqueueResponse("news-api-articles.json")

        val newsSource = "bbc-news"

        service.fetchArticles(newsSource).subscribe { response, _ ->
            assertTrue(response != null)
            assertEquals(10, response.articles.size)
            assertEquals("American eclipse - Live", response.articles[0].title)
            assertEquals("BBC News", response.articles[8].author)
        }

        val request = mockServer.takeRequest()
        assertEquals("/$ENDPOINT_ARTICLES?$PARAM_SOURCE=$newsSource", request.path)
    }

    @Test
    fun getNewsSources() {
        enqueueResponse("news-api-sources.json")

        service.fetchSources().subscribe { response, _ ->
            assertTrue(response != null)
            assertEquals(70, response.sources.size)
            assertEquals("al-jazeera-english", response.sources[1].id)
            assertEquals("business", response.sources[10].category)
            assertEquals("us", response.sources[39].country)
            assertEquals("The Telegraph", response.sources[61].name)
        }

        val request = mockServer.takeRequest()
        assertEquals("/$ENDPOINT_SOURCES", request.path)
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String> = mapOf()) {
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/" + fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers)
            mockResponse.addHeader(key, value)
        mockServer.enqueue(mockResponse.setBody(source.readString(Charset.forName("UTF-8"))))
    }
}
