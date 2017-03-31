package com.voltek.materialnewsfeed.api

data class NewsApiArticlesResponse(val articles: List<Article>)

data class Article(
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String)

