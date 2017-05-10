package com.voltek.newsfeed.presentation

import com.voltek.newsfeed.data.entity.Article
import com.voltek.newsfeed.data.entity.Source

/**
 * UI input events
 */
sealed class Event {

    class OpenWebsite(val url: String = "") : Event()

    class OpenNewsSources : Event()

    class OpenArticleDetails(val article: Article) : Event()

    class EnableNewsSource(val source: Source): Event()

    class Refresh : Event()

    class Share : Event()

    class FilterSources(val filter: String, val id: Int) : Event()
}
