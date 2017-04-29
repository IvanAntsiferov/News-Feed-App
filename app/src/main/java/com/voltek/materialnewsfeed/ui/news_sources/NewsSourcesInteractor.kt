package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.ui.BaseInteractor
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class NewsSourcesInteractor : BaseInteractor<NewsSourcesModel> {

    val NewsSourcesState: BehaviorSubject<NewsSourcesModel> = BehaviorSubject.createDefault(null)

    override fun StateChangesFeed(): Observable<NewsSourcesModel> = NewsSourcesState

    @Inject
    lateinit var mNewsSourcesRepo: DataProvider.NewsSources

    init {
        NewsApp.interactorComponent.inject(this)
    }
}
