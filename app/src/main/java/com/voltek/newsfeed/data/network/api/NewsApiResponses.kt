package com.voltek.newsfeed.data.network.api

import com.voltek.newsfeed.data.entity.ArticleRAW
import com.voltek.newsfeed.data.entity.SourceRAW

data class NewsApiArticlesResponse(val articles: List<ArticleRAW>)

data class NewsApiSourcesResponse(val sources: List<SourceRAW>)
