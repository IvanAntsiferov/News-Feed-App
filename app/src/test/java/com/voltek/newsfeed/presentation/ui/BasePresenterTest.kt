package com.voltek.newsfeed.presentation.ui

import com.voltek.newsfeed.presentation.navigation.proxy.Command
import com.voltek.newsfeed.presentation.navigation.proxy.Router
import org.junit.Before

abstract class BasePresenterTest {

    protected val queue = ArrayList<Command>()
    protected val router: Router = object : Router {

        override fun execute(command: Command) {
            queue.add(command)
        }
    }

    @Before
    fun clearQueue() {
        queue.clear()
    }
}
