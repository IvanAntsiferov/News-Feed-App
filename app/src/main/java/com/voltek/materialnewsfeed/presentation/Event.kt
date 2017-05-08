package com.voltek.materialnewsfeed.presentation

import com.voltek.materialnewsfeed.data.entity.Article

/**
 * UI input events
 */
sealed class Event {

    class OpenWebsite(val url: String = "") : Event()

    class OpenNewsSources : Event()

    class OpenArticleDetails(val article: Article) : Event()

    class Refresh : Event()

    class Share : Event()

    class FilterSources(val filter: String, val id: Int) : Event()
}
