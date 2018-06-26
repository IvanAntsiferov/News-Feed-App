package com.voltek.newsfeed.presentation.ui.newssources

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.MockData
import com.voltek.newsfeed.analytics.Analytics
import com.voltek.newsfeed.domain.usecase.newssources.EnableNewsSourceUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.base.Event
import com.voltek.newsfeed.presentation.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsSourcesPresenterTest : BasePresenterTest() {

    private lateinit var newsSourcesPresenter: NewsSourcesPresenter

    private val sourceUI = MockData.sourceUI()

    @Mock
    lateinit var newsSources: NewsSourcesUseCase

    @Mock
    lateinit var newsSourceEnable: EnableNewsSourceUseCase

    @Mock
    lateinit var view: NewsSourcesView

    @Mock
    lateinit var analytics: Analytics

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        newsSourcesPresenter = NewsSourcesPresenter(newsSources, newsSourceEnable, analytics)
    }

    @Test
    fun lifecycleTest() {
        newsSourcesPresenter.attachView(view)
        verify(view).attachInputListeners()
        verify(newsSources).execute(any(), any(), any(), any())
        newsSourcesPresenter.detachView(view)
        verify(view).detachInputListeners()
        newsSourcesPresenter.onDestroy()
        verify(newsSources).unsubscribe()
        verify(newsSourceEnable).unsubscribe()
    }

    @Test
    fun eventFilterSources() {
        newsSourcesPresenter.attachView(view)
        newsSourcesPresenter.event(Event.FilterSources("", -1))
        verify(newsSources, times(2)).execute(any(), any(), any(), any())
        verify(view, times(2)).render(any())
    }

    @Test
    fun eventRefresh() {
        newsSourcesPresenter.attachView(view)
        newsSourcesPresenter.event(Event.Refresh())
        verify(newsSources, times(2)).execute(any(), any(), any(), any())
        verify(view, times(2)).render(any())
    }

    @Test
    fun eventEnableNewsSource() {
        newsSourcesPresenter.attachView(view)
        newsSourcesPresenter.event(Event.EnableNewsSource(sourceUI))
        verify(newsSources).execute(any(), any(), any(), any())
        verify(newsSourceEnable).execute(any(), any(), any(), any())
        verify(view).render(any())
    }
}
