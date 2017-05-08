package com.voltek.materialnewsfeed.domain.interactor.articles

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.domain.interactor.BaseInteractor
import com.voltek.materialnewsfeed.domain.interactor.Result
import com.voltek.materialnewsfeed.domain.repository.ArticlesRepository
import com.voltek.materialnewsfeed.domain.repository.NewsSourcesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetArticlesInteractor constructor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<Article>?, Unit>(jobScheduler, uiScheduler) {

    @Inject
    lateinit var mArticlesRepo: ArticlesRepository

    @Inject
    lateinit var mNewsSourcesRepo: NewsSourcesRepository

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: Unit?): Observable<Result<List<Article>?>> =
            mNewsSourcesRepo.getCategory("")
                    .flatMap { mArticlesRepo.get(it.data ?: ArrayList()) }
}
