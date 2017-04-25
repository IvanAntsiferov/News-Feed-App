package com.voltek.materialnewsfeed.navigation

import com.voltek.materialnewsfeed.navigation.proxy.Navigator
import com.voltek.materialnewsfeed.navigation.proxy.RouterBus
import com.voltek.materialnewsfeed.navigation.proxy.RouterBinder
import com.voltek.materialnewsfeed.navigation.proxy.NavigatorCommand

/**
 * Manages app navigation, lifecycle tied to app process. Can hold only one navigator at a time.
 * When RouterBus receives command, it will be executed by current navigator or added to queue,
 * if there is no navigator attached or current navigator can not execute it.
 * When new navigator get attached, holder will try to execute queued commands.
 */
class RouterHolder : RouterBus, RouterBinder {

    private var navigator: Navigator? = null

    private var commandsQueue: NavigatorCommand? = null

    // RouterBus
    override fun execute(command: NavigatorCommand) {
        // If navigator = null returns false,
        // If command executed, returns true,
        // if command cannot be executed - false.
        if (!(navigator?.executeCommand(command) ?: false))
            if (command.addToQueue)
                commandsQueue = command
    }

    // RouterBinder
    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        executeQueue()
    }

    override fun removeNavigator() {
        this.navigator = null
    }

    private fun executeQueue() {
        if (commandsQueue != null && // If queue if not empty
                navigator != null && // If navigator attached
                (navigator?.executeCommand(commandsQueue!!) ?: false)) // Try to execute command
            commandsQueue = null // Remove command, if it was executed
    }
}
