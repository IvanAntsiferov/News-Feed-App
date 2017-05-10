package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.db.DatabaseDelegate
import com.voltek.newsfeed.data.network.ApiDelegate
import com.voltek.newsfeed.data.platform.ResourcesDelegate
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    // Main modules
    @Provides
    fun provideDatabase(): DatabaseDelegate = DatabaseDelegate()

    @Provides
    fun provideApi(): ApiDelegate = ApiDelegate()

    // Interfaces to access data
    @Provides
    fun provideDatabaseNewsSources(database: DatabaseDelegate): Provider.Database.NewsSources = database

    @Provides
    fun provideApiArticles(api: ApiDelegate): Provider.Api.Articles = api

    @Provides
    fun provideApiNewsSources(api: ApiDelegate): Provider.Api.NewsSources = api

    @Provides
    fun provideResources(): Provider.Platform.Resources = ResourcesDelegate()
}
