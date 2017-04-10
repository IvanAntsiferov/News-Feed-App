package com.voltek.materialnewsfeed.di

import com.voltek.materialnewsfeed.data.ArticlesRepository
import com.voltek.materialnewsfeed.ui.list.ListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface MainComponent {

    fun inject(repository: ArticlesRepository)

    fun inject(presenter: ListPresenter)
}
