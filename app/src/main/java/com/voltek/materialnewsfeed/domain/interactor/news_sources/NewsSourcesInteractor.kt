package com.voltek.materialnewsfeed.domain.interactor.news_sources

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.domain.interactor.BaseInteractor
import com.voltek.materialnewsfeed.domain.interactor.Result
import com.voltek.materialnewsfeed.domain.repository.NewsSourcesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class NewsSourcesInteractor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<Source>?, String>(jobScheduler, uiScheduler) {

    companion object {
        const val REFRESH = "REFRESH"
    }

    @Inject
    lateinit var mNewsSourcesRepo: NewsSourcesRepository

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: String?): Observable<Result<List<Source>?>> {
        if (parameter == null) {
            return mNewsSourcesRepo.getAll()
        } else if (parameter == REFRESH) {
            return mNewsSourcesRepo.refresh()
        } else {
            return mNewsSourcesRepo.getCategory(parameter)
        }
    }
}
