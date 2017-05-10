package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.domain.interactor.articles.GetArticlesInteractor
import com.voltek.newsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class InteractorModule(val jobScheduler: Scheduler, val uiScheduler: Scheduler) {

    @Provides
    fun provideGetArticlesInteractor(): GetArticlesInteractor =
            GetArticlesInteractor(jobScheduler, uiScheduler)

    @Provides
    fun provideNewsSourcesInteractor(): NewsSourcesInteractor =
            NewsSourcesInteractor(jobScheduler, uiScheduler)
}
