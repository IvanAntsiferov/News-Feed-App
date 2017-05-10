package com.voltek.newsfeed.data.network.api

import com.voltek.newsfeed.data.entity.Article
import com.voltek.newsfeed.data.entity.Source

data class NewsApiArticlesResponse(val articles: List<Article>)

data class NewsApiSourcesResponse(val sources: List<Source>)
