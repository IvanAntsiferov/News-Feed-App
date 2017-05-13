package com.voltek.newsfeed.presentation

import com.voltek.newsfeed.data.entity.ArticleRAW
import com.voltek.newsfeed.data.entity.SourceRAW

/**
 * UI input events
 */
sealed class Event {

    class OpenWebsite(val url: String = "") : Event()

    class OpenNewsSources : Event()

    class OpenArticleDetails(val article: ArticleRAW) : Event()

    class EnableNewsSource(val source: SourceRAW): Event()

    class Refresh : Event()

    class Share : Event()

    class FilterSources(val filter: String, val id: Int) : Event()
}
