package com.voltek.materialnewsfeed.ui

import com.voltek.materialnewsfeed.data.entity.Article

sealed class Event {

    class OpenNewsSources : Event()

    class OpenArticleDetails(val article: Article) : Event()

    class Refresh : Event()
}
