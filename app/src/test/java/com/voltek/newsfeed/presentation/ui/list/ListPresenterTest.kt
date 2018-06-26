package com.voltek.newsfeed.presentation.ui.list

import com.nhaarman.mockito_kotlin.any
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
import org.junit.Test
import org.mockito.Mock

class ListPresenterTest : BasePresenterTest<ListPresenter>() {

    private val articleUI = MockData.articleUI()

    @Mock
    lateinit var articles: GetArticlesUseCase
    @Mock
    lateinit var newsSourcesChanges: NewsSourcesUpdatesUseCase
    @Mock
    lateinit var view: ListView

    override fun initPresenter() = ListPresenter(router, articles, newsSourcesChanges)

    @Test
    fun lifecycleTest() {
        presenter.attachView(view)
        verify(articles).execute(any(), any(), any(), any())
        verify(newsSourcesChanges).execute(any(), any(), any(), any())
        verify(view).render(any())
        verify(view).attachInputListeners()
        presenter.detachView(view)
        verify(view).detachInputListeners()
        presenter.onDestroy()
        verify(articles).unsubscribe()
        verify(newsSourcesChanges).unsubscribe()
    }

    @Test
    fun eventOpenArticleDetails() {
        presenter.event(Event.OpenArticleDetails(articleUI))
        assertTrue(queue[0] is CommandOpenArticleDetailsScreen)
        assertEquals(1, queue.size)
        assertEquals(articleUI.url, (queue[0] as CommandOpenArticleDetailsScreen).article.url)
    }

    @Test
    fun eventOpenNewsSources() {
        presenter.event(Event.OpenNewsSources())
        assertTrue(queue[0] is CommandOpenNewsSourcesScreen)
        assertEquals(1, queue.size)
    }

    @Test
    fun eventRefresh() {
        presenter.attachView(view)
        presenter.event(Event.Refresh())
        verify(articles).execute(any(), any(), any(), any())
        verify(view).render(any())
    }
}
