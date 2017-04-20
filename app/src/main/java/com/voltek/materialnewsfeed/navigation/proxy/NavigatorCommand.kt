package com.voltek.materialnewsfeed.navigation.proxy

/**
 * Держит информацию о команде для навигатора.
 * @id уникальный индентификатор команды
 * @addToQueue добавлять ли команду в очередь, если она не может быть выполнена мгновенно.
 */
abstract class NavigatorCommand(val id: Int, val addToQueue: Boolean = true)
