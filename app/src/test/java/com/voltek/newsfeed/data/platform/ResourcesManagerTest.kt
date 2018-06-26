package com.voltek.newsfeed.data.platform

import android.content.Context
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ResourcesManagerTest {

    private lateinit var resourcesManager: ResourcesManager

    @Mock
    lateinit var context: Context

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        resourcesManager = ResourcesManager(context)
    }

    @Test
    fun getString() {
        val id = 12345
        val string = "string"
        whenever(context.getString(id)).thenReturn(string)
        assertEquals(string, resourcesManager.getString(id))
        verify(context).getString(id)
    }
}
