package com.voltek.materialnewsfeed.data.api

import io.reactivex.Observable
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
    ): Observable<NewsApiSourcesResponse>
}
