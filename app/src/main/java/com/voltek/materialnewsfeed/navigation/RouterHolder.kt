package com.voltek.materialnewsfeed.navigation

import com.voltek.materialnewsfeed.navigation.command.CommandNavigatorAttached
import com.voltek.materialnewsfeed.navigation.proxy.Navigator
import com.voltek.materialnewsfeed.navigation.proxy.RouterBus
import com.voltek.materialnewsfeed.navigation.proxy.RouterBinder
import com.voltek.materialnewsfeed.navigation.proxy.Command
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Manages app navigation, lifecycle tied to app process. Can hold only one navigator at a time.
 * When RouterBus receives command, it will be executed by current navigator or added to queue,
 * if there is no navigator attached or current navigator can not execute it.
 * When new navigator get attached, holder will try to execute queued commands.
 */
class RouterHolder : RouterBus, RouterBinder {

    val CommandsFeed: PublishSubject<Command> = PublishSubject.create()

    private var navigator: Navigator? = null

    private var commandsQueue: ArrayList<Command> = ArrayList()

    init {
        CommandsFeed
                .filter { !runCommand(it) } // If command executed
                .filter { !runQueue(it) } // If new navigator attached
                .subscribe({ addToQueue(it) }, Timber::e) // Else, try to add command to queue
    }

    // RouterBus
    override fun execute(command: Command) {
        CommandsFeed.onNext(command)
    }

    // RouterBinder
    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        CommandsFeed.onNext(CommandNavigatorAttached())
    }

    override fun removeNavigator() {
        this.navigator = null
    }

    // Private logic

    /**
     * @return If navigator = null returns false, if command executed - returns true,
     *         if command cannot be executed - false.
     */
    private fun runCommand(command: Command): Boolean = navigator?.executeCommand(command) ?: false

    /**
     * @return true, if new navigator has been attached (tries to execute all commands in queue)
     *         else returns false. (Without queue execution)
     */
    private fun runQueue(command: Command): Boolean {
        if (command is CommandNavigatorAttached) {
            commandsQueue
                    .filter { runCommand(it) }
                    .forEach { removeFromQueue(it) }

            return true
        } else {
            return false
        }
    }

    /**
     * @return true, if command was added to queue, else false.
     */
    private fun addToQueue(command: Command): Boolean {
        if (command.addToQueue) {
            command.id = commandsQueue[commandsQueue.lastIndex].id + 1
            return commandsQueue.add(command)
        } else {
            return false
        }
    }

    /**
     * @return true, if command removed successfully, else false
     */
    private fun removeFromQueue(command: Command) = commandsQueue.remove(command)
}
