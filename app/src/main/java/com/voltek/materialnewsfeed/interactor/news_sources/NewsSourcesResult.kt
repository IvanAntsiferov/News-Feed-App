package com.voltek.materialnewsfeed.interactor.news_sources

import com.voltek.materialnewsfeed.data.entity.Article

data class NewsSourcesResult(
        val articles: List<Article>? = null,
        val message: String = ""
)
