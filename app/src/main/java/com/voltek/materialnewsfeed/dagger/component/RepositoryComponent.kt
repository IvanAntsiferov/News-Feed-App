package com.voltek.materialnewsfeed.dagger.component

import com.voltek.materialnewsfeed.domain.repository.ArticlesRepository
import com.voltek.materialnewsfeed.domain.repository.NewsSourcesRepository
import com.voltek.materialnewsfeed.dagger.module.DataModule
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
