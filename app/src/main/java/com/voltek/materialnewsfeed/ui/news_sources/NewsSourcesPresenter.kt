package com.voltek.materialnewsfeed.ui.news_sources

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesModel
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView

@InjectViewState
class NewsSourcesPresenter : MvpPresenter<NewsSourcesView>() {

    private var mModel: NewsSourcesModel = NewsSourcesModel()

    fun notify(event: Event) {
        when (event) {
            is Event.Filter -> {}
            is Event.Refresh -> {}
        }
    }

    private fun updateModel() {
        viewState.render(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)

        loadNewsSources("")
    }

    override fun attachView(view: NewsSourcesView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: NewsSourcesView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    private fun loadNewsSources(filter: String) {
        mModel.sources.clear()
        mModel.loading = true
        mModel.message = ""
        updateModel()

        //
    }
}
