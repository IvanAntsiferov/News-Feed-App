package com.voltek.newsfeed.analytics


class ComposedAnalytics(private val kits: List<AnalyticsKit>) : Analytics {

    override fun articleView(title: String, url: String, source: String) {
        sendArticle(EVENT_ARTICLE_VIEW, title, url, source)
    }

    override fun articleShare(title: String, url: String, source: String) {
        sendArticle(EVENT_ARTICLE_SHARE, title, url, source)
    }

    override fun newsSourceEnable(title: String, url: String, category: String) {
        sendNewsSource(EVENT_NEWS_SOURCE_ENABLE, title, url, category)
    }

    override fun newsSourceDisable(title: String, url: String, category: String) {
        sendNewsSource(EVENT_NEWS_SOURCE_DISABLE, title, url, category)
    }

    override fun newsSourcesFilter(category: String) {
        sendEvent(EVENT_NEWS_SOURCE_FILTER, mapOf(
                PARAM_CATEGORY to category
        ))
    }

    private fun sendArticle(event: String, title: String, url: String, source: String) {
        sendEvent(event, mapOf(
                PARAM_TITLE to title,
                PARAM_URL to url,
                PARAM_SOURCE to source
        ))
    }

    private fun sendNewsSource(event: String, title: String, url: String, category: String) {
        sendEvent(event, mapOf(
                PARAM_TITLE to title,
                PARAM_URL to url,
                PARAM_CATEGORY to category
        ))
    }

    private fun sendEvent(name: String, params: Map<String, Any>) {
        kits.forEach { it.sendEvent(name, params) }
    }
}
