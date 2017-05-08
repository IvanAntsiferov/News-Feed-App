package com.voltek.materialnewsfeed.presentation

import com.voltek.materialnewsfeed.data.entity.Article

/**
 * UI input events
 */
sealed class Event {

    class OpenWebsite(val url: String = "") : com.voltek.materialnewsfeed.presentation.Event()

    class OpenNewsSources : com.voltek.materialnewsfeed.presentation.Event()

    class OpenArticleDetails(val article: com.voltek.materialnewsfeed.data.entity.Article) : com.voltek.materialnewsfeed.presentation.Event()

    class Refresh : com.voltek.materialnewsfeed.presentation.Event()

    class Share : com.voltek.materialnewsfeed.presentation.Event()

    class FilterSources(val filter: String, val id: Int) : com.voltek.materialnewsfeed.presentation.Event()
}
