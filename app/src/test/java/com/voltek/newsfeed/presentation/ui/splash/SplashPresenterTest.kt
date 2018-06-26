package com.voltek.newsfeed.presentation.ui.splash

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.ui.BasePresenterTest
import org.junit.Test
import org.mockito.Mock

class SplashPresenterTest : BasePresenterTest<SplashPresenter>() {

    @Mock
    lateinit var newsSourcesUseCase: NewsSourcesUseCase
    @Mock
    lateinit var view: SplashView

    override fun initPresenter() = SplashPresenter(router, newsSourcesUseCase)

    @Test
    fun initTest() {
        presenter.attachView(view)
        verify(newsSourcesUseCase).execute(any(), any(), any(), any())
        verify(view).attachInputListeners()
        presenter.detachView(view)
        verify(view).detachInputListeners()
        presenter.onDestroy()
        verify(newsSourcesUseCase).unsubscribe()
    }
}
