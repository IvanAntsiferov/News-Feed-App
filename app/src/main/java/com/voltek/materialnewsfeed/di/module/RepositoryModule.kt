package com.voltek.materialnewsfeed.di.module

import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.repository.ArticlesRepository
import com.voltek.materialnewsfeed.data.repository.NewsSourcesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideArticlesRepository(): ArticlesRepository = ArticlesRepository()

    @Provides
    fun provideArticles(repository: ArticlesRepository): DataProvider.Articles = repository

    @Provides
    fun provideNewsSourcesRepository(): NewsSourcesRepository = NewsSourcesRepository()

    @Provides
    fun provideNewsSources(repository: NewsSourcesRepository): DataProvider.NewsSources = repository
}
