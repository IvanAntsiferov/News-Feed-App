package com.voltek.newsfeed.domain.repository

import com.nhaarman.mockito_kotlin.whenever
import com.voltek.newsfeed.R
import com.voltek.newsfeed.data.network.NewsApi
import com.voltek.newsfeed.data.network.entity.ArticleAPI
import com.voltek.newsfeed.data.network.entity.NewsApiArticlesResponse
import com.voltek.newsfeed.data.platform.ResourcesManager
import com.voltek.newsfeed.domain.exception.NoConnectionException
import com.voltek.newsfeed.domain.exception.NoNewsSourcesSelectedException
import com.voltek.newsfeed.presentation.entity.SourceUI
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ArticlesRepositoryTest {

    private lateinit var articlesRepo: ArticlesRepository

    private val stringNoConnection = "stringNoConnection"
    private val stringServerError = "stringServerError"

    private val sources = arrayListOf(SourceUI(
            "id", "name", "description", "url", "category", "country", false))

    private val response = NewsApiArticlesResponse(
            arrayListOf(
                    ArticleAPI(
                            title = "title",
                            description = "description",
                            url = "url",
                            urlToImage = "urlToImage")))

    @Mock
    lateinit var api: NewsApi

    @Mock
    lateinit var res: ResourcesManager

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        articlesRepo = ArticlesRepository(api, res)
        whenever(res.getString(R.string.error_no_connection)).thenReturn(stringNoConnection)
        whenever(res.getString(R.string.error_retrieve_failed)).thenReturn(stringServerError)
    }

    @Test
    fun noSourcesSelected() {
        articlesRepo.get(ArrayList())
                .test()
                .assertError({ it is NoNewsSourcesSelectedException })
    }

    @Test
    fun apiExceptions() {
        whenever(api.fetchArticles(anyString())).thenReturn(Single.error(NoConnectionException()))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.data == null }
                .assertValue { it.message == stringNoConnection }
                .assertComplete()
        whenever(api.fetchArticles(anyString())).thenReturn(Single.error(Exception()))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.data == null }
                .assertValue { it.message == (stringServerError + sources[0].name) }
                .assertComplete()
    }

    @Test
    fun normalServerResponse() {
        whenever(api.fetchArticles(anyString())).thenReturn(Single.just(response))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data?.size == 2 } // Because of Header
                .assertValue { it.data!![1].title == response.articles[0].title }
                .assertComplete()
    }
}
