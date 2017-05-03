package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.data.repository.ArticlesRepository
import com.voltek.materialnewsfeed.data.repository.NewsSourcesRepository
import com.voltek.materialnewsfeed.di.module.AppModule
import com.voltek.materialnewsfeed.di.module.NetworkModule
import com.voltek.materialnewsfeed.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class)
)
interface DataComponent {

    fun inject(repository: ArticlesRepository)

    fun inject(repository: NewsSourcesRepository)
}
