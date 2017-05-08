package com.voltek.materialnewsfeed.dagger.component

import com.voltek.materialnewsfeed.dagger.module.InteractorModule
import com.voltek.materialnewsfeed.dagger.module.RepositoryModule
import com.voltek.materialnewsfeed.domain.interactor.articles.GetArticlesInteractor
import com.voltek.materialnewsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        InteractorModule::class,
        RepositoryModule::class)
)
interface InteractorComponent {

    fun inject(interactor: GetArticlesInteractor)

    fun inject(interactor: NewsSourcesInteractor)
}
