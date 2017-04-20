package com.voltek.materialnewsfeed.navigation.proxy

/**
 * Принимает команды для навигатора.
 */
interface RouterBus {

    fun execute(command: NavigatorCommand)
}
