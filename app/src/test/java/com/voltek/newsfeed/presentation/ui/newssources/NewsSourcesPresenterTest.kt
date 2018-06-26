package com.voltek.newsfeed.presentation.ui.newssources

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.MockData
import com.voltek.newsfeed.domain.usecase.newssources.EnableNewsSourceUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.base.Event
import com.voltek.newsfeed.presentation.ui.BasePresenterTest
import org.junit.Test
import org.mockito.Mock

class NewsSourcesPresenterTest : BasePresenterTest<NewsSourcesPresenter>() {

    private val sourceUI = MockData.sourceUI()

    @Mock
    lateinit var newsSources: NewsSourcesUseCase
    @Mock
    lateinit var newsSourceEnable: EnableNewsSourceUseCase
    @Mock
    lateinit var view: NewsSourcesView

    override fun initPresenter() = NewsSourcesPresenter(newsSources, newsSourceEnable, analytics)

    @Test
    fun lifecycleTest() {
        presenter.attachView(view)
        verify(view).attachInputListeners()
        verify(newsSources).execute(any(), any(), any(), any())
        presenter.detachView(view)
        verify(view).detachInputListeners()
        presenter.onDestroy()
        verify(newsSources).unsubscribe()
        verify(newsSourceEnable).unsubscribe()
    }

    @Test
    fun eventFilterSources() {
        val event = Event.FilterSources("random", -1)

        presenter.attachView(view)
        presenter.event(event)
        verify(newsSources, times(2)).execute(any(), any(), any(), any())
        verify(view, times(2)).render(any())
        verify(analytics).newsSourcesFilter(event.filter)
    }

    @Test
    fun eventRefresh() {
        presenter.attachView(view)
        presenter.event(Event.Refresh())
        verify(newsSources, times(2)).execute(any(), any(), any(), any())
        verify(view, times(2)).render(any())
    }

    @Test
    fun eventEnableNewsSource() {
        presenter.attachView(view)
        presenter.event(Event.EnableNewsSource(sourceUI))
        verify(newsSources).execute(any(), any(), any(), any())
        verify(newsSourceEnable).execute(any(), any(), any(), any())
        verify(view).render(any())
    }

    @Test
    fun enableDisableNewsSourceAnalytics() {
        val event = Event.EnableNewsSource(sourceUI)

        event.source.isEnabled = false
        presenter.event(Event.EnableNewsSource(sourceUI))
        verify(analytics).newsSourceEnable(sourceUI.name, sourceUI.url, sourceUI.category)

        event.source.isEnabled = true
        presenter.event(Event.EnableNewsSource(sourceUI))
        verify(analytics).newsSourceDisable(sourceUI.name, sourceUI.url, sourceUI.category)
    }
}
