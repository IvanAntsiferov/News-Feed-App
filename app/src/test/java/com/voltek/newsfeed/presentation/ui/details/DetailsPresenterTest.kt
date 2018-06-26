package com.voltek.newsfeed.presentation.ui.details

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.MockData
import com.voltek.newsfeed.analytics.Analytics
import com.voltek.newsfeed.presentation.base.Event
import com.voltek.newsfeed.presentation.entity.ArticleUI
import com.voltek.newsfeed.presentation.navigation.command.CommandBack
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenWebsite
import com.voltek.newsfeed.presentation.navigation.command.CommandShareArticle
import com.voltek.newsfeed.presentation.ui.BasePresenterTest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest : BasePresenterTest() {

    private lateinit var detailsPresenter: DetailsPresenter

    private val articleUI = MockData.articleUI()

    @Mock
    lateinit var view: DetailsView

    @Mock
    lateinit var analytics: Analytics

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        detailsPresenter = DetailsPresenter(router, analytics)
    }

    @Test
    fun setArticle() {
        // Normal case
        detailsPresenter.attachView(view)
        verify(view).attachInputListeners()
        detailsPresenter.setArticle(articleUI)
        verify(view).render(any())
        // Empty article case
        detailsPresenter.attachView(view)
        detailsPresenter.setArticle(ArticleUI())
        verify(view, times(2)).render(any())
        // Detach view
        detailsPresenter.detachView(view)
        verify(view).detachInputListeners()
    }

    @Test
    fun eventShare() {
        detailsPresenter.attachView(view)
        detailsPresenter.setArticle(articleUI)
        detailsPresenter.event(Event.Share())
        assertTrue(queue[0] is CommandShareArticle)
        assertEquals(articleUI.title, (queue[0] as CommandShareArticle).title)
        assertEquals(articleUI.url, (queue[0] as CommandShareArticle).url)
    }

    @Test
    fun eventOpenWebsite() {
        detailsPresenter.attachView(view)
        detailsPresenter.setArticle(articleUI)
        detailsPresenter.event(Event.OpenWebsite())
        assertTrue(queue[0] is CommandOpenWebsite)
        assertEquals(1, queue.size)
        assertEquals(articleUI.url, (queue[0] as CommandOpenWebsite).url)
    }

    @Test
    fun eventOpenNewsSources() {
        detailsPresenter.attachView(view)
        detailsPresenter.event(Event.OpenNewsSources())
        assertTrue(queue[0] is CommandOpenNewsSourcesScreen)
        assertEquals(1, queue.size)
    }

    @Test
    fun eventBack() {
        detailsPresenter.attachView(view)
        detailsPresenter.event(Event.Back())
        assertTrue(queue[0] is CommandBack)
        assertEquals(1, queue.size)
    }
}
