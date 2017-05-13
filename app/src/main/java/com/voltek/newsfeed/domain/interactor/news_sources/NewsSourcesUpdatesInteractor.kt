package com.voltek.newsfeed.domain.interactor.news_sources

import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.domain.interactor.BaseInteractor
import com.voltek.newsfeed.domain.interactor.Parameter
import com.voltek.newsfeed.domain.interactor.Result
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Notify presenter, if enabled news sources has changed.
 */
class NewsSourcesUpdatesInteractor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<Unit?, Unit>(jobScheduler, uiScheduler) {

    @Inject
    lateinit var mNewsSourcesRepo: NewsSourcesRepository

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: Parameter<Unit?>): Observable<Result<Unit?>> =
            mNewsSourcesRepo.getSourcesEnabledObservable()
                    .map { Result(null) }
}
