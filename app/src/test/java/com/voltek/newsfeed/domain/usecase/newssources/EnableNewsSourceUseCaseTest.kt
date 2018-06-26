package com.voltek.newsfeed.domain.usecase.newssources

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.voltek.newsfeed.MockData
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import com.voltek.newsfeed.domain.usecase.BaseUseCaseTest
import com.voltek.newsfeed.domain.usecase.Parameter
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class EnableNewsSourceUseCaseTest : BaseUseCaseTest() {

    private lateinit var enableNewsSourceUseCase: EnableNewsSourceUseCase

    private val sourceUI = MockData.sourceUI()

    @Mock
    lateinit var newsSourcesRepository: NewsSourcesRepository

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        enableNewsSourceUseCase =
                EnableNewsSourceUseCase(newsSourcesRepository, scheduler, scheduler)
    }

    @Test
    fun executeNormal() {
        whenever(newsSourcesRepository.update(sourceUI.id, !sourceUI.isEnabled))
                .thenReturn(Completable.complete())
        var error = false
        var completed = false
        enableNewsSourceUseCase.execute(
                Parameter(item = sourceUI),
                Consumer {},
                Consumer {
                    error = true
                },
                Action {
                    completed = true
                }
        )
        assertFalse(error)
        assertTrue(completed)
        verify(newsSourcesRepository).update(sourceUI.id, !sourceUI.isEnabled)
    }

    @Test
    fun executeWithNull() {
        var error = false
        var completed = false
        var emittedResult = false
        var emittedMessage = ""
        enableNewsSourceUseCase.execute(
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
        assertTrue(error)
        assertFalse(completed)
        assertFalse(emittedResult)
        assertTrue(emittedMessage.isEmpty())
        verify(newsSourcesRepository, times(0)).update(any(), any())
    }
}
