package com.voltek.materialnewsfeed.navigation.proxy

/**
 * Entry point for navigation commands.
 */
interface RouterBus {

    fun execute(command: Command)
}
