package com.voltek.newsfeed.analytics.kits

import com.voltek.newsfeed.Logger
import com.voltek.newsfeed.analytics.AnalyticsKit

class DebugAnalyticsKit : AnalyticsKit {

    override fun sendEvent(name: String, params: Map<String, Any>) {
        Logger.d("$name $params")
    }
}
