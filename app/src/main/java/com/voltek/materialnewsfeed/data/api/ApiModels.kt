package com.voltek.materialnewsfeed.data.api

import org.parceler.Parcel

data class NewsApiArticlesResponse(val articles: List<Article>)

@Parcel
data class Article(
        val author: String? = null,
        val title: String? = null,
        val description: String? = null,
        val url: String? = null,
        val urlToImage: String? = null,
        val publishedAt: String? = null)

data class NewsApiSourcesResponse(val sources: List<Source>)

data class Source(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val language: String,
        val country: String)
