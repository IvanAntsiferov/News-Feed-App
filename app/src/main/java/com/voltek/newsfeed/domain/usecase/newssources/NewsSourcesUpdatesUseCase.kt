package com.voltek.newsfeed.domain.usecase.newssources

import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import com.voltek.newsfeed.domain.usecase.BaseUseCase
import com.voltek.newsfeed.domain.usecase.Parameter
import com.voltek.newsfeed.domain.usecase.Result
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit

/**
 * Notify presenter, if enabled news sources has changed.
 */
class NewsSourcesUpdatesUseCase(
        private val newsSourcesRepository: NewsSourcesRepository,
        jobScheduler: Scheduler, uiScheduler: Scheduler
)  : BaseUseCase<Unit?, Unit>(jobScheduler, uiScheduler) {

    override fun buildObservable(parameter: Parameter<Unit?>): Observable<Result<Unit?>> =
            newsSourcesRepository
                    .getSourcesEnabledObservable()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map { Result(null) }
}
