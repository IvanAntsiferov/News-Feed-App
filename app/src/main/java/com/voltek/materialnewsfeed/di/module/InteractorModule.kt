package com.voltek.materialnewsfeed.di.module

import com.voltek.materialnewsfeed.interactor.articles.GetArticlesInteractor
import com.voltek.materialnewsfeed.interactor.news_sources.GetNewsSourcesInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class InteractorModule(val jobScheduler: Scheduler, val uiScheduler: Scheduler) {

    @Provides
    fun provideGetArticlesInteractor(): GetArticlesInteractor =
            GetArticlesInteractor(jobScheduler, uiScheduler)

    @Provides
    fun provideGetNewsSourcesInteractor(): GetNewsSourcesInteractor =
            GetNewsSourcesInteractor(jobScheduler, uiScheduler)
}
