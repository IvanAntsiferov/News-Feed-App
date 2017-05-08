package com.voltek.materialnewsfeed.dagger.component

import com.voltek.materialnewsfeed.dagger.module.InteractorModule
import com.voltek.materialnewsfeed.dagger.module.RouterModule
import com.voltek.materialnewsfeed.presentation.details.DetailsPresenter
import com.voltek.materialnewsfeed.presentation.list.ListPresenter
import com.voltek.materialnewsfeed.presentation.news_sources.NewsSourcesPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        RouterModule::class,
        InteractorModule::class)
)
interface PresenterComponent {

    fun inject(presenter: ListPresenter)

    fun inject(presenter: DetailsPresenter)

    fun inject(presenter: NewsSourcesPresenter)
}
