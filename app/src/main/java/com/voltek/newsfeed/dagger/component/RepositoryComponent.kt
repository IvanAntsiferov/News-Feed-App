package com.voltek.newsfeed.dagger.component

import com.voltek.newsfeed.domain.repository.ArticlesRepository
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import com.voltek.newsfeed.dagger.module.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        DataModule::class)
)
interface RepositoryComponent {

    fun inject(repository: ArticlesRepository)

    fun inject(repository: NewsSourcesRepository)
}
