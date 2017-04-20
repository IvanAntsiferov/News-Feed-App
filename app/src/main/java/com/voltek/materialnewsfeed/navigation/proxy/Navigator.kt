package com.voltek.mvpdemo.library.navigation.proxy

/**
 * Этот интерфейс реализуется классом, который способен выполнять обязанности навигатора.
 * Содержит единственный метод, который возвращает true, если навигатор поддерживает данный тип
 * команд, и false, если не поддерживает.
 */
interface Navigator {

    fun executeCommand(commandId: NavigatorCommand): Boolean
}
