package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.ui.BaseEvent

sealed class NewsSourcesEvent : BaseEvent {

    class filter(val category: String) : NewsSourcesEvent()
}
