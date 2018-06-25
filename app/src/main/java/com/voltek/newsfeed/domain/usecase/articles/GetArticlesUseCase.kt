package com.voltek.newsfeed.domain.usecase.articles

import com.voltek.newsfeed.domain.repository.ArticlesRepository
import com.voltek.newsfeed.domain.repository.NewsSourcesRepository
import com.voltek.newsfeed.domain.usecase.BaseUseCase
import com.voltek.newsfeed.domain.usecase.Parameter
import com.voltek.newsfeed.domain.usecase.Result
import com.voltek.newsfeed.presentation.entity.ArticleUI
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetArticlesUseCase constructor(
        private val articlesRepository: ArticlesRepository,
        private val newsSourcesRepository: NewsSourcesRepository,
        jobScheduler: Scheduler, uiScheduler: Scheduler
) : BaseUseCase<List<ArticleUI>?, ArticleUI>(jobScheduler, uiScheduler) {

    override fun buildObservable(parameter: Parameter<ArticleUI?>): Observable<Result<List<ArticleUI>?>> =
            newsSourcesRepository
                    .getCategory("")
                    .toObservable()
                    .flatMap { articlesRepository.get(it.data ?: ArrayList()) }
}
