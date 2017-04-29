package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.data.entity.Source

sealed class NewsSourcesModel {

    class Loading : NewsSourcesModel()

    class Result(items: List<Source>) : NewsSourcesModel()

    class Error(message: String) : NewsSourcesModel()
}
