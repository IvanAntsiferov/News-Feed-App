package com.voltek.newsfeed.data.network.entity

import android.support.annotation.Keep

@Keep
data class NewsApiArticlesResponse(val articles: List<ArticleAPI>)
