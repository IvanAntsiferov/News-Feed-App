package com.voltek.materialnewsfeed.domain.interactor.news_sources

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.domain.interactor.BaseInteractor
import com.voltek.materialnewsfeed.domain.interactor.Parameter
import com.voltek.materialnewsfeed.domain.interactor.Result
import com.voltek.materialnewsfeed.domain.repository.NewsSourcesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class NewsSourcesInteractor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<Source>?, Source>(jobScheduler, uiScheduler) {

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

    override fun buildObservable(parameter: Parameter<Source?>): Observable<Result<List<Source>?>> {
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
