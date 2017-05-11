package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.domain.interactor.articles.GetArticlesInteractor
import com.voltek.newsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class InteractorModule(
        val uiScheduler: Scheduler,
        val ioScheduler: Scheduler,
        val computationScheduler: Scheduler
) {

    @Provides
    fun provideGetArticlesInteractor(): GetArticlesInteractor =
            GetArticlesInteractor(ioScheduler, uiScheduler)

    @Provides
    fun provideNewsSourcesInteractor(): NewsSourcesInteractor =
            NewsSourcesInteractor(ioScheduler, uiScheduler)
}
