package com.voltek.materialnewsfeed.api

data class NewsApiArticlesResponse(val articles: List<Article>)

data class Article(
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String)

data class NewsApiSourcesResponse(val sources: List<Source>)

data class Source(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val language: String,
        val country: String)
