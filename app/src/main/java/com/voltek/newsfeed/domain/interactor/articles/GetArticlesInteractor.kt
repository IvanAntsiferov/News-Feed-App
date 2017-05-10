package com.voltek.newsfeed.domain.interactor.articles

import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.data.entity.Article
import com.voltek.newsfeed.domain.interactor.BaseInteractor
import com.voltek.newsfeed.domain.interactor.Parameter
import com.voltek.newsfeed.domain.interactor.Result
import com.voltek.newsfeed.domain.repository.ArticlesRepository
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetArticlesInteractor constructor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<Article>?, Article>(jobScheduler, uiScheduler) {

    @Inject
    lateinit var mArticlesRepo: ArticlesRepository

    @Inject
    lateinit var mNewsSourcesRepo: NewsSourcesRepository

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: Parameter<Article?>): Observable<Result<List<Article>?>> =
            mNewsSourcesRepo.getCategory("")
                    .flatMap { mArticlesRepo.get(it.data ?: ArrayList()) }
}
