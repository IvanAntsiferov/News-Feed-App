package com.voltek.materialnewsfeed.di.module

import com.voltek.materialnewsfeed.data.repository.ArticlesRepository
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.repository.NewsSourcesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideArticles(): DataProvider.Articles = ArticlesRepository()

    @Provides
    fun provideNewsSources(): DataProvider.NewsSources = NewsSourcesRepository()
}
