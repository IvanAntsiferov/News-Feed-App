package com.voltek.materialnewsfeed.data.network.api

import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source

data class NewsApiArticlesResponse(val articles: List<Article>)

data class NewsApiSourcesResponse(val sources: List<Source>)
