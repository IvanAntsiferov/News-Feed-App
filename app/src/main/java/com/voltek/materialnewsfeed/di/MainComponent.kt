package com.voltek.materialnewsfeed.di

import com.voltek.materialnewsfeed.mvp.NewsModel
import com.voltek.materialnewsfeed.ui.list.ListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface MainComponent {

    fun inject(model: NewsModel)

    fun inject(presenter: ListPresenter)
}
