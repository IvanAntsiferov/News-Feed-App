package com.voltek.newsfeed.domain.interactor.news_sources

import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.data.entity.SourceRAW
import com.voltek.newsfeed.domain.interactor.BaseInteractor
import com.voltek.newsfeed.domain.interactor.Parameter
import com.voltek.newsfeed.domain.interactor.Result
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class NewsSourcesInteractor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<SourceRAW>?, SourceRAW>(jobScheduler, uiScheduler) {

    companion object {
        // Flags, using for specifying type of performing operation
        const val GET = "GET"
        const val ENABLE = "ENABLE"
        const val REFRESH = "REFRESH"
    }

    @Inject
    lateinit var mNewsSourcesRepo: NewsSourcesRepository

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: Parameter<SourceRAW?>): Observable<Result<List<SourceRAW>?>> {
        if (parameter.flag == ENABLE && parameter.item != null) {
            val source = parameter.item
            source.isEnabled = !source.isEnabled
            return mNewsSourcesRepo.update(source).map { Result(null) }
        } else if (parameter.flag == REFRESH) {
            return mNewsSourcesRepo.refresh()
        } else if (parameter.flag == GET) {
            return mNewsSourcesRepo.getAll()
        } else {
            return mNewsSourcesRepo.getCategory(parameter.flag)
        }
    }
}
