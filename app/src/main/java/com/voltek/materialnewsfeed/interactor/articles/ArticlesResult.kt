package com.voltek.materialnewsfeed.interactor.articles

import com.voltek.materialnewsfeed.data.entity.Article

data class ArticlesResult(
        val articles: List<Article>? = null,
        val message: String = ""
)
