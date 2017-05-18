package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.storage.NewsSourcesStorage
import com.voltek.newsfeed.data.network.ApiDelegate
import com.voltek.newsfeed.data.platform.ResourcesManager
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    // Main modules
    @Provides
    fun provideDatabase(): NewsSourcesStorage = NewsSourcesStorage()

    @Provides
    fun provideApi(): ApiDelegate = ApiDelegate()

    // Interfaces to access data
    @Provides
    fun provideDatabaseNewsSources(database: NewsSourcesStorage): Provider.Storage.NewsSources = database

    @Provides
    fun provideApiArticles(api: ApiDelegate): Provider.Api.Articles = api

    @Provides
    fun provideApiNewsSources(api: ApiDelegate): Provider.Api.NewsSources = api

    @Provides
    fun provideResources(): Provider.Platform.Resources = ResourcesManager()
}
