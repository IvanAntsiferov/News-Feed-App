package com.voltek.materialnewsfeed.navigation.proxy

/**
 * Entry point for navigation commands.
 */
interface Router {

    fun execute(command: Command)
}
