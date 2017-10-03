package com.voltek.newsfeed.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nhaarman.mockito_kotlin.whenever
import com.voltek.newsfeed.domain.exception.NoConnectionException
import junit.framework.Assert.assertTrue
import okhttp3.Interceptor
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ConnectivityInterceptorTest {

    private lateinit var connectivityInterceptor: ConnectivityInterceptor

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Mock
    lateinit var networkInfo: NetworkInfo

    @Mock
    lateinit var chain: Interceptor.Chain

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        connectivityInterceptor = ConnectivityInterceptor(context)
        whenever(connectivityManager.activeNetworkInfo)
                .thenReturn(networkInfo)
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .thenReturn(connectivityManager)
    }

    @Test
    fun hasConnection() {
        whenever(networkInfo.isConnected).thenReturn(true)
        try {
            connectivityInterceptor.intercept(chain)
        } catch (e: Exception) {
            assertTrue(e is IllegalStateException)
        }
    }

    @Test
    fun doNotHasConnection() {
        whenever(networkInfo.isConnected).thenReturn(false)
        try {
            connectivityInterceptor.intercept(chain)
        } catch (e: Exception) {
            assertTrue(e is NoConnectionException)
        }
    }
}
