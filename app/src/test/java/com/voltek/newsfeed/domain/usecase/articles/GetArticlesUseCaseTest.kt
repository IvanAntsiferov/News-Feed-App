package com.voltek.newsfeed.domain.usecase.articles

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.voltek.newsfeed.MockData
import com.voltek.newsfeed.domain.repository.ArticlesRepository
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import com.voltek.newsfeed.domain.usecase.BaseUseCaseTest
import com.voltek.newsfeed.domain.usecase.Parameter
import com.voltek.newsfeed.domain.usecase.Result
import com.voltek.newsfeed.presentation.entity.ArticleUI
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetArticlesUseCaseTest : BaseUseCaseTest() {

    private lateinit var getArticlesUseCase: GetArticlesUseCase

    private val sourceUI = MockData.sourceUI()
    private val articleUI = MockData.articleUI()

    @Mock
    lateinit var articlesRepository: ArticlesRepository

    @Mock
    lateinit var newsSourcesRepository: NewsSourcesRepository

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        getArticlesUseCase = GetArticlesUseCase(
                articlesRepository, newsSourcesRepository, scheduler, scheduler)
    }

    @Test
    fun execute() {
        whenever(newsSourcesRepository.getCategory(""))
                .thenReturn(Single.just(Result(arrayListOf(sourceUI))))
        whenever(articlesRepository.get(arrayListOf(sourceUI)))
                .thenReturn(Observable.just(Result(arrayListOf(articleUI))))
        var error = false
        var completed = false
        var emittedResult = ArticleUI()
        var emittedMessage = ""
        getArticlesUseCase.execute(
                Parameter(),
                Consumer {
                    emittedResult = it.data!![0]
                    emittedMessage = it.message
                },
                Consumer {
                    error = true
                },
                Action {
                    completed = true
                }
        )
        assertEquals(articleUI.title, emittedResult.title)
        assertTrue(emittedMessage.isEmpty())
        assertFalse(error)
        assertTrue(completed)
        verify(newsSourcesRepository, times(1)).getCategory("")
        verify(articlesRepository, times(1)).get(arrayListOf(sourceUI))
    }
}
