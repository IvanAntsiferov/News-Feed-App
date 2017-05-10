package com.voltek.newsfeed.navigation.proxy

/**
 * Entry point for navigation commands.
 */
interface Router {

    fun execute(command: Command)
}
