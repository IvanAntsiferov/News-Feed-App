package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.di.module.ModelModule
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ModelModule::class)
)
interface PresenterComponent {

    fun inject(presenter: NewsSourcesPresenter)
}
