package com.voltek.materialnewsfeed.dagger.module

import com.voltek.materialnewsfeed.domain.interactor.articles.GetArticlesInteractor
import com.voltek.materialnewsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class InteractorModule(val jobScheduler: Scheduler, val uiScheduler: Scheduler) {

    @Provides
    fun provideGetArticlesInteractor(): GetArticlesInteractor =
            GetArticlesInteractor(jobScheduler, uiScheduler)

    @Provides
    fun provideGetNewsSourcesInteractor(): NewsSourcesInteractor =
            NewsSourcesInteractor(jobScheduler, uiScheduler)
}
