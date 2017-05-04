package com.voltek.materialnewsfeed.interactor.news_sources

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.interactor.BaseInteractor
import com.voltek.materialnewsfeed.interactor.Result
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetNewsSourcesInteractor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<Source>?, String>(jobScheduler, uiScheduler) {

    @Inject
    lateinit var mNewsSourcesRepo: DataProvider.NewsSources

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: String?): Observable<Result<List<Source>?>> =
            mNewsSourcesRepo.getAll()
}
