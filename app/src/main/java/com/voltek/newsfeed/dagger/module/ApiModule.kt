package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.network.ApiDelegate
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideApiArticles(): Provider.Api.Articles = ApiDelegate()

    @Provides
    fun provideApiNewsSources(): Provider.Api.NewsSources = ApiDelegate()
}
