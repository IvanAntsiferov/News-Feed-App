package com.voltek.materialnewsfeed.navigation.proxy

/**
 * Basic class for navigation commands. Extend from it and add any properties, that you need.
 * @id command unique identifier
 * @addToQueue should command be added to queue, if it cannot be executed instantly
 */
abstract class NavigatorCommand(val id: Int, val addToQueue: Boolean = true)
