package com.voltek.materialnewsfeed.ui.news_sources

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.voltek.materialnewsfeed.ui.BaseEvent
import com.voltek.materialnewsfeed.ui.BaseInteractor
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject
import timber.log.Timber

@InjectViewState
class NewsSourcesPresenter : MvpPresenter<NewsSourcesView>() {

    private val mInteractor: BaseInteractor<NewsSourcesModel, NewsSourcesEvent> =
            NewsSourcesInteractor()

    private val mStateChanges: Disposable

    init {
        mStateChanges = mInteractor.stateChangesFeed()
                .subscribe({ viewState.render(it) }, Timber::e)
    }

    override fun attachView(view: NewsSourcesView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: NewsSourcesView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    override fun onDestroy() {
        mStateChanges.dispose()
    }

    fun inputEventsFeed() = mInteractor.inputEventsFeed()
}
