package com.voltek.newsfeed.presentation.ui.splash

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
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
        splashPresenter.attachView(view)
        verify(newsSourcesUseCase).execute(any(), any(), any(), any())
        verify(view).attachInputListeners()
        splashPresenter.detachView(view)
        verify(view).detachInputListeners()
        splashPresenter.onDestroy()
        verify(newsSourcesUseCase).unsubscribe()
    }
}
