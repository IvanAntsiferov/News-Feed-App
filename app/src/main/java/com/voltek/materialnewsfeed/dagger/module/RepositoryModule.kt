package com.voltek.materialnewsfeed.dagger.module

import com.voltek.materialnewsfeed.domain.repository.ArticlesRepository
import com.voltek.materialnewsfeed.domain.repository.NewsSourcesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideArticlesRepository(): ArticlesRepository = ArticlesRepository()

    @Provides
    fun provideNewsSourcesRepository(): NewsSourcesRepository = NewsSourcesRepository()
}
