package com.voltek.newsfeed.analytics

interface Analytics {

    fun articleView(title: String, url: String, source: String)

    fun articleShare(title: String, url: String, source: String)

    fun newsSourceEnable(title: String, url: String, category: String)

    fun newsSourceDisable(title: String, url: String, category: String)

    fun newsSourcesFilter(category: String)
}
