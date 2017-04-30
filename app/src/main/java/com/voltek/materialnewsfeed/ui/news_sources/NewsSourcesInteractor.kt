package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.ui.BaseInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class NewsSourcesInteractor : BaseInteractor<NewsSourcesModel, NewsSourcesEvent> {

    @Inject
    lateinit var mNewsSourcesRepo: DataProvider.NewsSources

    private val stateChangesBehavior: BehaviorSubject<NewsSourcesModel> =
            BehaviorSubject.createDefault(NewsSourcesModel.Loading())

    private val inputEventsBehavior: PublishSubject<NewsSourcesEvent> =
            PublishSubject.create()

    override fun stateChangesFeed(): Observable<NewsSourcesModel> = stateChangesBehavior

    override fun inputEventsFeed(): Subject<NewsSourcesEvent> = inputEventsBehavior

    init {
        NewsApp.interactorComponent.inject(this)

        load()

        inputEventsBehavior
                .subscribe({
                    when (it) {
                        is NewsSourcesEvent.filter -> filter(it.category)
                    }
                })
    }

    private fun filter(category: String) {
        stateChangesBehavior.onNext(NewsSourcesModel.Result(mNewsSourcesRepo.getCategory(category)))
    }

    private fun load() {
        mNewsSourcesRepo.getAll()
                .startWith { NewsSourcesModel.Loading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    stateChangesBehavior.onNext(NewsSourcesModel.Result(it))
                }, {
                    stateChangesBehavior.onNext(NewsSourcesModel.Error(it.message.toString()))
                })
    }
}
