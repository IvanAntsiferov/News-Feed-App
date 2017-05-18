package com.voltek.newsfeed.dagger.component

import com.voltek.newsfeed.dagger.module.RepositoryModule
import com.voltek.newsfeed.domain.interactor.articles.GetArticlesInteractor
import com.voltek.newsfeed.domain.interactor.news_sources.EnableNewsSourceInteractor
import com.voltek.newsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import com.voltek.newsfeed.domain.interactor.news_sources.NewsSourcesUpdatesInteractor
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        RepositoryModule::class)
)
interface InteractorComponent {

    fun inject(interactor: GetArticlesInteractor)

    fun inject(interactor: NewsSourcesInteractor)

    fun inject(interactor: NewsSourcesUpdatesInteractor)

    fun inject(interactor: EnableNewsSourceInteractor)
}
