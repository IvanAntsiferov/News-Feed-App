package com.voltek.materialnewsfeed.di

import com.voltek.materialnewsfeed.data.ArticlesRepository
import com.voltek.materialnewsfeed.data.NewsSourcesRepository
import com.voltek.materialnewsfeed.ui.list.ListPresenter
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface MainComponent {

    // Repositories
    fun inject(repository: ArticlesRepository)

    fun inject(repository: NewsSourcesRepository)

    // Presenters
    fun inject(presenter: ListPresenter)

    fun inject(presenter: NewsSourcesPresenter)
}
