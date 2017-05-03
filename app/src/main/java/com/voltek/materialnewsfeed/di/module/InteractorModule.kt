package com.voltek.materialnewsfeed.di.module

import com.voltek.materialnewsfeed.interactor.articles.GetArticlesInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class InteractorModule(val jobScheduler: Scheduler, val uiScheduler: Scheduler) {

    @Provides
    fun provideGetArticlesInteractor(): GetArticlesInteractor =
            GetArticlesInteractor(jobScheduler, uiScheduler)
}
