package com.voltek.newsfeed.domain.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.voltek.newsfeed.R
import com.voltek.newsfeed.TestUtils
import com.voltek.newsfeed.data.network.NewsApi
import com.voltek.newsfeed.data.network.entity.NewsApiSourcesResponse
import com.voltek.newsfeed.data.platform.ResourcesManager
import com.voltek.newsfeed.data.storage.NewsSourcesStorage
import com.voltek.newsfeed.domain.exception.NoConnectionException
import com.voltek.newsfeed.domain.exception.WrongNewsSourceIdException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsSourcesRepositoryTest {

    private lateinit var newsSourcesRepo: NewsSourcesRepository

    private val stringNoConnection = TestUtils.stringNoConnection
    private val stringServerError = TestUtils.stringServerError
    private val stringCategoryAll = "stringCategoryAll"
    private val stringCategoryEnabled = "stringCategoryEnabled"
    private val stringNoNewsSourcesLoaded = "stringNoNewsSourcesLoaded"
    private val stringNoNewsSourcesSelectedYet = "stringNoNewsSourcesSelectedYet"
    private val stringNoNewsSourcesForCategory = "stringNoNewsSourcesForCategory"

    private val responseSources = NewsApiSourcesResponse(arrayListOf(TestUtils.sourceAPI()))

    private val sourceDB = TestUtils.sourceDB()

    @Mock
    lateinit var api: NewsApi

    @Mock
    lateinit var storage: NewsSourcesStorage

    @Mock
    lateinit var res: ResourcesManager

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        newsSourcesRepo = NewsSourcesRepository(api, storage, res)

        whenever(res.getString(R.string.error_no_connection))
                .thenReturn(stringNoConnection)
        whenever(res.getString(R.string.error_request_failed))
                .thenReturn(stringServerError)
        whenever(res.getString(R.string.category_all))
                .thenReturn(stringCategoryAll)
        whenever(res.getString(R.string.category_enabled))
                .thenReturn(stringCategoryEnabled)
        whenever(res.getString(R.string.error_no_news_sources_loaded))
                .thenReturn(stringNoNewsSourcesLoaded)
        whenever(res.getString(R.string.error_no_news_sources_selected_yet))
                .thenReturn(stringNoNewsSourcesSelectedYet)
        whenever(res.getString(R.string.error_no_news_sources_for_category))
                .thenReturn(stringNoNewsSourcesForCategory)
    }

    @Test
    fun updateWithWrongID() {
        val id = "10"
        whenever(storage.findById(id)).thenReturn(null)
        newsSourcesRepo.update(id, false)
                .test()
                .assertError { it is WrongNewsSourceIdException }
        verify(storage, times(1)).findById(id)
    }

    @Test
    fun updateNormal() {
        val id = "10"
        whenever(storage.findById(id)).thenReturn(sourceDB)
        newsSourcesRepo.update(id, false)
                .test()
                .assertComplete()
        verify(storage, times(1)).findById(id)
        verify(storage, times(1)).save(arrayListOf(sourceDB))
    }

    @Test
    fun getAllWithEmptyCache() {
        whenever(api.fetchSources()).thenReturn(Single.just(responseSources))
        whenever(storage.queryAll()).thenReturn(ArrayList())
        newsSourcesRepo.getAll()
                .test()
                .assertNoErrors()
        verify(storage, times(2)).queryAll()
        verify(storage, times(1)).save(any())
        verify(api, times(1)).fetchSources()
    }

    @Test
    fun getAllWithCache() {
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        newsSourcesRepo.getAll()
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
        verify(storage, times(1)).queryAll()
        verify(storage, times(0)).save(any())
        verify(api, times(0)).fetchSources()
    }

    @Test
    fun getAllWIthAPIErrors() {
        whenever(storage.queryAll()).thenReturn(ArrayList())
        whenever(api.fetchSources()).thenReturn(Single.error(NoConnectionException()))
        newsSourcesRepo.getAll()
                .test()
                .assertValue { it.message == stringNoConnection }
                .assertValue { it.data == null }
                .assertComplete()
        whenever(api.fetchSources()).thenReturn(Single.error(Exception()))
        newsSourcesRepo.getAll()
                .test()
                .assertValue { it.message == stringServerError }
                .assertValue { it.data == null }
                .assertComplete()
        verify(res, times(1)).getString(R.string.error_no_connection)
        verify(res, times(1)).getString(R.string.error_request_failed)
        verify(storage, times(2)).queryAll()
        verify(storage, times(0)).save(any())
        verify(api, times(2)).fetchSources()
    }

    @Test
    fun getRandomCategory() {
        val category = "category"
        whenever(storage.queryCategory(category)).thenReturn(arrayListOf(sourceDB))
        newsSourcesRepo.getCategory(category)
                .test()
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()
        verify(storage, times(1)).queryCategory(category)
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun getWrongCategory() {
        val category = "category"
        whenever(storage.queryCategory(category)).thenReturn(ArrayList())
        newsSourcesRepo.getCategory(category)
                .test()
                .assertValue { it.message == stringNoNewsSourcesForCategory }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()
        verify(res, times(1)).getString(R.string.error_no_news_sources_for_category)
        verify(storage, times(1)).queryCategory(category)
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun getAllCategoryNormal() {
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        newsSourcesRepo.getCategory(stringCategoryAll)
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()
        verify(storage, times(1)).queryAll()
        verify(storage, times(0)).queryCategory(anyString())
    }

    @Test
    fun getAllCategoryEmpty() {
        whenever(storage.queryAll()).thenReturn(ArrayList())
        newsSourcesRepo.getCategory(stringCategoryAll)
                .test()
                .assertValue { it.message == stringNoNewsSourcesLoaded }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()
        verify(res, times(1)).getString(R.string.error_no_news_sources_loaded)
        verify(storage, times(1)).queryAll()
        verify(storage, times(0)).queryCategory(anyString())
    }

    @Test
    fun getEnabledCategoryNormal() {
        whenever(storage.queryEnabled()).thenReturn(arrayListOf(sourceDB))

        newsSourcesRepo.getCategory(stringCategoryEnabled)
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()

        newsSourcesRepo.getCategory("")
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()

        verify(storage, times(2)).queryEnabled()
        verify(storage, times(0)).queryCategory(anyString())
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun getEnabledCategoryEmpty() {
        whenever(storage.queryEnabled()).thenReturn(arrayListOf())

        newsSourcesRepo.getCategory(stringCategoryEnabled)
                .test()
                .assertValue { it.message == stringNoNewsSourcesSelectedYet }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()

        newsSourcesRepo.getCategory("")
                .test()
                .assertValue { it.message == stringNoNewsSourcesSelectedYet }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()

        verify(res, times(2)).getString(R.string.error_no_news_sources_selected_yet)
        verify(storage, times(2)).queryEnabled()
        verify(storage, times(0)).queryCategory(anyString())
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun refreshWithAPIErrors() {
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        whenever(api.fetchSources()).thenReturn(Single.error(NoConnectionException()))
        newsSourcesRepo.refresh()
                .test()
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertValue { it.message == stringNoConnection }
                .assertComplete()

        whenever(storage.queryAll()).thenReturn(ArrayList())
        whenever(api.fetchSources()).thenReturn(Single.error(Exception()))
        newsSourcesRepo.refresh()
                .test()
                .assertValue { it.data!!.isEmpty() }
                .assertValue { it.message == stringServerError }
                .assertComplete()

        verify(api, times(2)).fetchSources()
        verify(res, times(1)).getString(R.string.error_no_connection)
        verify(res, times(1)).getString(R.string.error_request_failed)
        verify(storage, times(2)).queryAll()
        verify(storage, times(0)).queryEnabled()
        verify(storage, times(0)).save(any())
        verify(storage, times(0)).deleteAll()
    }

    @Test
    fun refreshNormal() {
        whenever(api.fetchSources()).thenReturn(Single.just(responseSources))
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        whenever(storage.queryEnabled()).thenReturn(ArrayList())
        newsSourcesRepo.refresh()
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()
        verify(api, times(1)).fetchSources()
        verify(res, times(0)).getString(anyInt())
        verify(storage, times(1)).queryAll()
        verify(storage, times(1)).queryEnabled()
        verify(storage, times(1)).save(any())
        verify(storage, times(1)).deleteAll()
    }
}
