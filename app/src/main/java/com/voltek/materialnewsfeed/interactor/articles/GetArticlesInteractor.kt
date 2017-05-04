package com.voltek.materialnewsfeed.interactor.articles

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.interactor.BaseInteractor
import com.voltek.materialnewsfeed.interactor.Result
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetArticlesInteractor constructor(jobScheduler: Scheduler, uiScheduler: Scheduler)
    : BaseInteractor<List<Article>?, Unit>(jobScheduler, uiScheduler) {

    @Inject
    lateinit var mArticlesRepo: DataProvider.Articles

    @Inject
    lateinit var mNewsSourcesRepo: DataProvider.NewsSources

    init {
        NewsApp.interactorComponent.inject(this)
    }

    override fun buildObservable(parameter: Unit?): Observable<Result<List<Article>?>> =
            mNewsSourcesRepo.getCategory("")
                    .flatMap { mArticlesRepo.get(it.data ?: ArrayList()) }
}
