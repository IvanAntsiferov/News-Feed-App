package com.voltek.newsfeed.dagger.component

import com.voltek.newsfeed.dagger.module.InteractorModule
import com.voltek.newsfeed.dagger.module.RouterModule
import com.voltek.newsfeed.presentation.details.DetailsPresenter
import com.voltek.newsfeed.presentation.list.ListPresenter
import com.voltek.newsfeed.presentation.news_sources.NewsSourcesPresenter
import com.voltek.newsfeed.presentation.splash.SplashPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        RouterModule::class,
        InteractorModule::class)
)
interface PresenterComponent {

    fun inject(presenter: SplashPresenter)

    fun inject(presenter: ListPresenter)

    fun inject(presenter: DetailsPresenter)

    fun inject(presenter: NewsSourcesPresenter)
}
