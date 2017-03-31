package com.voltek.materialnewsfeed.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v1/articles")
    fun getArticles(
            @Query("apiKey") apiKey: String,
            @Query("source") source: String
    ): Observable<NewsApiArticlesResponse>

    @GET("/v1/sources")
    fun getSources(
            @Query("apiKey") apiKey: String,
            @Query("category") category: String
    ): Observable<String>
}
