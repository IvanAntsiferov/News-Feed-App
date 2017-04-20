package com.voltek.materialnewsfeed.navigation

import com.voltek.materialnewsfeed.navigation.proxy.Navigator
import com.voltek.materialnewsfeed.navigation.proxy.RouterBus
import com.voltek.materialnewsfeed.navigation.proxy.RouterBinder
import com.voltek.materialnewsfeed.navigation.proxy.NavigatorCommand

/**
 * Управляет навигацией. Живет столько же, сколько приложение.
 * Одновременно может содержать лишь один навигатор, которому передает выполнение команд.
 * Если команда не может быть выполнена - она записывается в очередь.
 * Как только подключается новый навигатор, пытается с помощью него выполнить всю текущую
 * очередь команд.
 */
class RouterHolder : RouterBus, RouterBinder {

    private val commandsQueue: ArrayList<NavigatorCommand> = ArrayList()

    private var navigator: Navigator? = null

    // Реализация интерфейса RouterBus
    override fun execute(command: NavigatorCommand) {
        // Если navigator = null вернет false,
        // если он может выполнить команду, вернет true,
        // если не может - false.
        if (!(navigator?.executeCommand(command) ?: false)) {
            // Добавит в очередь, если в команде не был переопредлен параметр addToQueue.
            if (command.addToQueue)
                commandsQueue.add(command)
        }
    }

    // Реализация интерфейса RouterBinder
    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        executeQueue()
    }

    override fun removeNavigator() {
        this.navigator = null
    }

    // Вызывается при подключении нового навигатора
    private fun executeQueue() {
        // Для каждой команды в очереди, начиная с первой добавленой
        for (command in commandsQueue) {
            // Если навигатор выполняет команду, удалить ее из очереди
            if (navigator?.executeCommand(command) ?: false) {
                commandsQueue.remove(command)
            }
        }
    }
}
