package com.voltek.newsfeed.analytics

class ComposedAnalytics(private val kits: List<AnalyticsKit>) : Analytics {



    private fun sendEvent(name: String, params: Map<String, Any>) {
        kits.forEach { it.sendEvent(name, params) }
    }
}
