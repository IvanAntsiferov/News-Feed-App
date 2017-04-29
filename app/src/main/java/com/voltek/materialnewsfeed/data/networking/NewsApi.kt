package com.voltek.materialnewsfeed.data.networking

import com.voltek.materialnewsfeed.data.networking.response.NewsApiArticlesResponse
import com.voltek.materialnewsfeed.data.networking.response.NewsApiSourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v1/articles")
    fun fetchArticles(
            @Query("apiKey") apiKey: String,
            @Query("source") source: String
    ): Call<NewsApiArticlesResponse>

    @GET("/v1/sources")
    fun fetchSources(
            @Query("apiKey") apiKey: String
    ): Call<NewsApiSourcesResponse>
}
