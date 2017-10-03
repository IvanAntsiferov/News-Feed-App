package com.voltek.newsfeed.presentation.ui.splash

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.domain.use_case.news_sources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SplashPresenterTest : BasePresenterTest() {

    private lateinit var splashPresenter: SplashPresenter

    @Mock
    lateinit var newsSourcesUseCase: NewsSourcesUseCase

    @Mock
    lateinit var view: SplashView

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        splashPresenter = SplashPresenter(router, newsSourcesUseCase)
    }

    @Test
    fun initTest() {
        verify(newsSourcesUseCase, times(1)).execute(any(), any(), any(), any())
        splashPresenter.attachView(view)
        verify(view, times(1)).attachInputListeners()
        splashPresenter.detachView(view)
        verify(view, times(1)).detachInputListeners()
        splashPresenter.onDestroy()
        verify(newsSourcesUseCase, times(1)).unsubscribe()
    }
}
