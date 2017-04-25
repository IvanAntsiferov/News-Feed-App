package com.voltek.materialnewsfeed.navigation.proxy

/**
 * Class, that implements navigator, calls setNavigator when ready to perform in app navigation.
 * (onResume for activity class)
 * When it can no longer provide navigation, calls removeNavigator.
 * (onPause for activity class)
 */
interface RouterBinder {

    fun setNavigator(navigator: Navigator)

    fun removeNavigator()
}
