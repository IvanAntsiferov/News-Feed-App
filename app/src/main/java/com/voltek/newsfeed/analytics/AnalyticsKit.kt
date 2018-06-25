package com.voltek.newsfeed.analytics

interface AnalyticsKit {

    fun sendEvent(name: String, params: Map<String, Any>)
}
