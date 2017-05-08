package com.voltek.materialnewsfeed.presentation.news_sources

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import com.voltek.materialnewsfeed.presentation.Event
import com.voltek.materialnewsfeed.presentation.news_sources.NewsSourcesContract.NewsSourcesModel
import com.voltek.materialnewsfeed.presentation.news_sources.NewsSourcesContract.NewsSourcesView
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject

@InjectViewState
class NewsSourcesPresenter : MvpPresenter<NewsSourcesView>() {

    @Inject
    lateinit var mNewsSources: NewsSourcesInteractor

    private var mModel: NewsSourcesModel = NewsSourcesModel()

    fun notify(event: Event) {
        when (event) {
            is Event.FilterSources -> {
                mModel.categoryId = event.id
                loadNewsSources(event.filter)
            }
            is Event.Refresh -> {
                mModel.resetId()
                loadNewsSources(NewsSourcesInteractor.REFRESH)
            }
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
