package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.domain.repository.ArticlesRepository
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideArticlesRepository(): ArticlesRepository = ArticlesRepository()

    @Provides
    @Singleton
    fun provideNewsSourcesRepository(): NewsSourcesRepository = NewsSourcesRepository()
}
