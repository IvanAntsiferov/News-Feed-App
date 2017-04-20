package com.voltek.materialnewsfeed.navigation.proxy

/**
 * Интерфейс, доступ к которому предоставляется из класса приложения.
 * Его вызывает класс, реализующий интерфейс Navigator.
 * Как только этот класс готов предоставлять навигацию, он вызывает setNavigator(this).
 * (Для Activity это метод onResume)
 * Как только класс более не готов предоставлять навигацию он вызывает removeNavigator().
 * Отписывать навигатор от роутера стоит каждый раз, когда он более не способен мгоновенно
 * обработать команду. (Для activity это метод onPause)
 */
interface RouterBinder {

    fun setNavigator(navigator: Navigator)

    fun removeNavigator()
}
