package com.voltek.materialnewsfeed.ui.news_sources

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.interactor.news_sources.GetNewsSourcesInteractor
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesModel
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class NewsSourcesPresenter : MvpPresenter<NewsSourcesView>() {

    @Inject
    lateinit var mNewsSources: GetNewsSourcesInteractor

    private var mModel: NewsSourcesModel = NewsSourcesModel()

    fun notify(event: Event) {
        when (event) {
            is Event.FilterSources -> {
                mModel.categoryId = event.id
                loadNewsSources(event.filter)
            }
            is Event.Refresh -> loadNewsSources(GetNewsSourcesInteractor.REFRESH)
        }
    }

    private fun updateModel() {
        viewState.render(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)
        loadNewsSources(null)
    }

    override fun attachView(view: NewsSourcesView?) {
        Timber.d("attachView")
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: NewsSourcesView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    override fun onDestroy() {
        mNewsSources.unsubscribe()
    }

    private fun loadNewsSources(filter: String?) {
        mModel.sources.clear()
        mModel.loading = true
        mModel.message = ""
        updateModel()

        mNewsSources.execute(
                filter,
                Consumer {
                    mModel.sources = ArrayList(it.data ?: ArrayList<Source>())
                    mModel.message = it.message
                    updateModel()
                },
                Consumer {
                    mModel.message = it.message ?: ""
                    finishLoading()
                },
                Action {
                    finishLoading()
                }
        )
    }

    private fun finishLoading() {
        mModel.loading = false
        mNewsSources.unsubscribe()
        updateModel()
    }
}
