package com.voltek.newsfeed.presentation.ui.details

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.MockData
import com.voltek.newsfeed.presentation.base.Event
import com.voltek.newsfeed.presentation.entity.ArticleUI
import com.voltek.newsfeed.presentation.navigation.command.CommandBack
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenWebsite
import com.voltek.newsfeed.presentation.navigation.command.CommandShareArticle
import com.voltek.newsfeed.presentation.ui.BasePresenterTest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.mockito.Mock

class DetailsPresenterTest : BasePresenterTest<DetailsPresenter>() {

    private val articleUI = MockData.articleUI()

    @Mock
    lateinit var view: DetailsView

    override fun initPresenter() = DetailsPresenter(router, analytics)

    @Test
    fun setArticle() {
        // Normal case
        presenter.attachView(view)
        verify(view).attachInputListeners()
        presenter.setArticle(articleUI)
        verify(analytics).articleView(articleUI.title!!, articleUI.url!!, articleUI.source)
        verify(view).render(any())
        // Empty article case
        presenter.attachView(view)
        presenter.setArticle(ArticleUI())
        verify(view, times(2)).render(any())
        // Detach view
        presenter.detachView(view)
        verify(view).detachInputListeners()
    }

    @Test
    fun eventShare() {
        presenter.attachView(view)
        presenter.setArticle(articleUI)
        presenter.event(Event.Share())
        assertTrue(queue[0] is CommandShareArticle)
        assertEquals(articleUI.title, (queue[0] as CommandShareArticle).title)
        assertEquals(articleUI.url, (queue[0] as CommandShareArticle).url)
        verify(analytics).articleShare(articleUI.title!!, articleUI.url!!, articleUI.source)
    }

    @Test
    fun eventOpenWebsite() {
        presenter.attachView(view)
        presenter.setArticle(articleUI)
        presenter.event(Event.OpenWebsite())
        assertTrue(queue[0] is CommandOpenWebsite)
        assertEquals(1, queue.size)
        assertEquals(articleUI.url, (queue[0] as CommandOpenWebsite).url)
    }

    @Test
    fun eventOpenNewsSources() {
        presenter.attachView(view)
        presenter.event(Event.OpenNewsSources())
        assertTrue(queue[0] is CommandOpenNewsSourcesScreen)
        assertEquals(1, queue.size)
    }

    @Test
    fun eventBack() {
        presenter.attachView(view)
        presenter.event(Event.Back())
        assertTrue(queue[0] is CommandBack)
        assertEquals(1, queue.size)
    }
}
