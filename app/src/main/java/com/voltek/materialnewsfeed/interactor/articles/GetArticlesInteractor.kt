package com.voltek.materialnewsfeed.interactor.articles

import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.interactor.BaseInteractor
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetArticlesInteractor constructor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<Article>, Unit>(jobScheduler, uiScheduler) {

    override fun buildObservable(parameter: Unit?): Observable<List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
