package com.voltek.mvpdemo.library.navigation.proxy

/**
 * Принимает команды для навигатора.
 */
interface RouterBus {

    fun execute(command: NavigatorCommand)
}
