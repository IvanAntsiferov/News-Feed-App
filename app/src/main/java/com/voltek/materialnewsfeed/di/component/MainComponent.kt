package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.data.repository.ArticlesRepository
import com.voltek.materialnewsfeed.data.repository.NewsSourcesRepository
import com.voltek.materialnewsfeed.di.module.AppModule
import com.voltek.materialnewsfeed.di.module.NetworkModule
import com.voltek.materialnewsfeed.di.module.RepositoryModule
import com.voltek.materialnewsfeed.ui.list.ListPresenter
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesPresenter
import dagger.Component
import javax.inject.Singleton

@Deprecated("Use specific components instead")
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        RepositoryModule::class,
        NetworkModule::class)
)
interface MainComponent {

    fun inject(repository: ArticlesRepository)

    fun inject(repository: NewsSourcesRepository)

    fun inject(presenter: ListPresenter)

    fun inject(presenter: NewsSourcesPresenter)
}
