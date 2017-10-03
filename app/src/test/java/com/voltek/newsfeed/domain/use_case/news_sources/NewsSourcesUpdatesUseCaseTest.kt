package com.voltek.newsfeed.domain.use_case.news_sources

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.voltek.newsfeed.CurrentThreadExecutor
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import com.voltek.newsfeed.domain.use_case.Parameter
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsSourcesUpdatesUseCaseTest {

    private lateinit var newsSourcesUpdatesUseCase: NewsSourcesUpdatesUseCase

    private val scheduler = Schedulers.from(CurrentThreadExecutor())

    @Mock
    lateinit var newsSourcesRepository: NewsSourcesRepository

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        newsSourcesUpdatesUseCase =
                NewsSourcesUpdatesUseCase(newsSourcesRepository, scheduler, scheduler)
    }

    @Test
    fun execute() {
        whenever(newsSourcesRepository.getSourcesEnabledObservable())
                .thenReturn(PublishSubject.create())
        var error = false
        var completed = false
        var emittedResult = false
        var emittedMessage = ""
        newsSourcesUpdatesUseCase.execute(
                Parameter(),
                Consumer {
                    emittedResult = true
                    emittedMessage = it.message
                },
                Consumer {
                    error = true
                },
                Action {
                    completed = true
                }
        )
        Assert.assertFalse(error)
        Assert.assertFalse(completed)
        Assert.assertFalse(emittedResult)
        Assert.assertTrue(emittedMessage.isEmpty())
        verify(newsSourcesRepository, times(1)).getSourcesEnabledObservable()
    }
}
