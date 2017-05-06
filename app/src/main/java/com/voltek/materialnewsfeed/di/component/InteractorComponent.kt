package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.di.module.InteractorModule
import com.voltek.materialnewsfeed.di.module.RepositoryModule
import com.voltek.materialnewsfeed.interactor.articles.GetArticlesInteractor
import com.voltek.materialnewsfeed.interactor.news_sources.GetNewsSourcesInteractor
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        InteractorModule::class,
        RepositoryModule::class)
)
interface InteractorComponent {

    fun inject(interactor: GetArticlesInteractor)

    fun inject(interactor: GetNewsSourcesInteractor)
}
