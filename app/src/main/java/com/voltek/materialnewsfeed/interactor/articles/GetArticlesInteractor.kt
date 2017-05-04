package com.voltek.materialnewsfeed.interactor.articles

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.interactor.BaseInteractor
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetArticlesInteractor constructor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<ArticlesResult, Unit>(jobScheduler, uiScheduler) {

    @Inject
    lateinit var mArticlesRepo: DataProvider.Articles

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: Unit?): Observable<ArticlesResult> = mArticlesRepo.get()
}
