package com.voltek.materialnewsfeed.presentation.news_sources

import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesModel
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView

@com.arellomobile.mvp.InjectViewState
class NewsSourcesPresenter : com.arellomobile.mvp.MvpPresenter<NewsSourcesView>() {

    @javax.inject.Inject
    lateinit var mNewsSources: com.voltek.materialnewsfeed.domain.interactor.news_sources.NewsSourcesInteractor

    private var mModel: com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesModel = com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesModel()

    fun notify(event: com.voltek.materialnewsfeed.ui.Event) {
        when (event) {
            is com.voltek.materialnewsfeed.ui.Event.FilterSources -> {
                mModel.categoryId = event.id
                loadNewsSources(event.filter)
            }
            is com.voltek.materialnewsfeed.ui.Event.Refresh -> {
                mModel.resetId()
                loadNewsSources(com.voltek.materialnewsfeed.domain.interactor.news_sources.NewsSourcesInteractor.Companion.REFRESH)
            }
        }
    }

    private fun updateModel() {
        viewState.render(mModel)
    }

    init {
        com.voltek.materialnewsfeed.NewsApp.Companion.presenterComponent.inject(this)
        loadNewsSources(null)
    }

    override fun attachView(view: com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView?) {
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
                io.reactivex.functions.Consumer {
                    mModel.sources = ArrayList(it.data ?: ArrayList<Source>())
                    mModel.message = it.message
                    updateModel()
                },
                io.reactivex.functions.Consumer {
                    mModel.message = it.message ?: ""
                    finishLoading()
                },
                io.reactivex.functions.Action {
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
