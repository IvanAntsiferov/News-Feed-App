package com.voltek.materialnewsfeed.di

import com.voltek.materialnewsfeed.data.repository.ArticlesRepository
import com.voltek.materialnewsfeed.data.repository.NewsSourcesRepository
import com.voltek.materialnewsfeed.ui.list.ListPresenter
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        DataModule::class,
        NetworkModule::class)
)
interface MainComponent {

    fun inject(repository: ArticlesRepository)

    fun inject(repository: NewsSourcesRepository)

    fun inject(presenter: ListPresenter)

    fun inject(presenter: NewsSourcesPresenter)
}
