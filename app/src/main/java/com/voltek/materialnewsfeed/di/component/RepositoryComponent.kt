package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.data.repository.ArticlesRepository
import com.voltek.materialnewsfeed.data.repository.NewsSourcesRepository
import com.voltek.materialnewsfeed.di.module.AppModule
import com.voltek.materialnewsfeed.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface RepositoryComponent {

    fun inject(repository: ArticlesRepository)

    fun inject(repository: NewsSourcesRepository)
}
