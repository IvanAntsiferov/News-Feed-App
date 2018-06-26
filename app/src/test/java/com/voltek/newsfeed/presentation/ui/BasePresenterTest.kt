package com.voltek.newsfeed.presentation.ui

import com.voltek.newsfeed.analytics.Analytics
import com.voltek.newsfeed.presentation.base.BasePresenter
import com.voltek.newsfeed.presentation.navigation.proxy.Command
import com.voltek.newsfeed.presentation.navigation.proxy.Router
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class BasePresenterTest<P : BasePresenter<*>> {

    protected lateinit var presenter: P

    protected val queue = ArrayList<Command>()
    protected val router: Router = object : Router {

        override fun execute(command: Command) {
            queue.add(command)
        }
    }

    @Mock
    lateinit var analytics: Analytics

    abstract fun initPresenter(): P

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = initPresenter()
    }

    @Before
    fun clearQueue() {
        queue.clear()
    }
}
