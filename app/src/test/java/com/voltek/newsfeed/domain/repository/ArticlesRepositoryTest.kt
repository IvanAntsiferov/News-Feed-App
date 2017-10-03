package com.voltek.newsfeed.domain.repository

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.voltek.newsfeed.R
import com.voltek.newsfeed.TestUtils
import com.voltek.newsfeed.data.network.NewsApi
import com.voltek.newsfeed.data.network.entity.NewsApiArticlesResponse
import com.voltek.newsfeed.data.platform.ResourcesManager
import com.voltek.newsfeed.domain.exception.NoConnectionException
import com.voltek.newsfeed.domain.exception.NoNewsSourcesSelectedException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ArticlesRepositoryTest {

    private lateinit var articlesRepo: ArticlesRepository

    private val stringNoConnection = TestUtils.stringNoConnection
    private val stringServerError = TestUtils.stringServerError

    private val sources = arrayListOf(TestUtils.sourceUI())

    private val response = NewsApiArticlesResponse(arrayListOf(TestUtils.articleAPI()))

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
        verify(api, times(0)).fetchArticles(anyString())
    }

    @Test
    fun apiExceptions() {
        whenever(api.fetchArticles(anyString())).thenReturn(Single.error(NoConnectionException()))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.data == null }
                .assertValue { it.message == stringNoConnection }
                .assertComplete()
        verify(api, times(1)).fetchArticles(anyString())
        whenever(api.fetchArticles(anyString())).thenReturn(Single.error(Exception()))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.data == null }
                .assertValue { it.message == (stringServerError + sources[0].name) }
                .assertComplete()
        verify(api, times(2)).fetchArticles(anyString())
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
        verify(api, times(1)).fetchArticles(anyString())
    }
}
