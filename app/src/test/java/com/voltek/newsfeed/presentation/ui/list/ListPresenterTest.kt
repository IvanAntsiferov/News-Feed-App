package com.voltek.newsfeed.presentation.ui.list

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.MockData
import com.voltek.newsfeed.domain.usecase.articles.GetArticlesUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUpdatesUseCase
import com.voltek.newsfeed.presentation.base.Event
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenArticleDetailsScreen
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import com.voltek.newsfeed.presentation.ui.BasePresenterTest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ListPresenterTest : BasePresenterTest() {

    private lateinit var listPresenter: ListPresenter

    private val articleUI = MockData.articleUI()

    @Mock
    lateinit var articles: GetArticlesUseCase

    @Mock
    lateinit var newsSourcesChanges: NewsSourcesUpdatesUseCase

    @Mock
    lateinit var view: ListView

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        listPresenter = ListPresenter(router, articles, newsSourcesChanges)
    }

    @Test
    fun lifecycleTest() {
        listPresenter.attachView(view)
        verify(articles, times(1)).execute(any(), any(), any(), any())
        verify(newsSourcesChanges, times(1)).execute(any(), any(), any(), any())
        verify(view, times(1)).render(any())
        verify(view, times(1)).attachInputListeners()
        listPresenter.detachView(view)
        verify(view, times(1)).detachInputListeners()
        listPresenter.onDestroy()
        verify(articles, times(1)).unsubscribe()
        verify(newsSourcesChanges, times(1)).unsubscribe()
    }

    @Test
    fun eventOpenArticleDetails() {
        listPresenter.event(Event.OpenArticleDetails(articleUI))
        assertTrue(queue[0] is CommandOpenArticleDetailsScreen)
        assertEquals(1, queue.size)
        assertEquals(articleUI.url, (queue[0] as CommandOpenArticleDetailsScreen).article.url)
    }

    @Test
    fun eventOpenNewsSources() {
        listPresenter.event(Event.OpenNewsSources())
        assertTrue(queue[0] is CommandOpenNewsSourcesScreen)
        assertEquals(1, queue.size)
    }

    @Test
    fun eventRefresh() {
        listPresenter.attachView(view)
        listPresenter.event(Event.Refresh())
        verify(articles, times(1)).execute(any(), any(), any(), any())
        verify(view, times(1)).render(any())
    }
}
