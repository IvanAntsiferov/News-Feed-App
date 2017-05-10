package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.domain.repository.ArticlesRepository
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideArticlesRepository(): ArticlesRepository = ArticlesRepository()

    @Provides
    fun provideNewsSourcesRepository(): NewsSourcesRepository = NewsSourcesRepository()
}
