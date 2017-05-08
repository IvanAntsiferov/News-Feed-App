package com.voltek.materialnewsfeed.dagger.module

import com.voltek.materialnewsfeed.data.Provider
import com.voltek.materialnewsfeed.data.db.DatabaseDelegate
import com.voltek.materialnewsfeed.data.network.ApiDelegate
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
}
