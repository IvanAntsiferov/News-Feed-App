package com.voltek.materialnewsfeed.presentation.list

import com.voltek.materialnewsfeed.presentation.Event
import com.voltek.materialnewsfeed.ui.list.ListContract.ListModel
import com.voltek.materialnewsfeed.ui.list.ListContract.ListView
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesActivity

@com.arellomobile.mvp.InjectViewState
class ListPresenter : com.arellomobile.mvp.MvpPresenter<ListView>() {

    @javax.inject.Inject
    lateinit var mRouter: com.voltek.materialnewsfeed.navigation.proxy.Router

    @javax.inject.Inject
    lateinit var mArticles: com.voltek.materialnewsfeed.domain.interactor.articles.GetArticlesInteractor

    // Holds current model through full presenter lifecycle
    private var mModel: com.voltek.materialnewsfeed.ui.list.ListContract.ListModel = com.voltek.materialnewsfeed.ui.list.ListContract.ListModel()

    // View notify presenter about events using this method
    fun notify(event: Event) {
        when (event) {
            is com.voltek.materialnewsfeed.ui.Event.OpenArticleDetails -> mRouter.execute(com.voltek.materialnewsfeed.ui.list.CommandOpenArticleDetails(event.article))
            is com.voltek.materialnewsfeed.ui.Event.OpenNewsSources -> mRouter.execute(com.voltek.materialnewsfeed.navigation.command.CommandStartActivity(com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesActivity()))
            is com.voltek.materialnewsfeed.ui.Event.Refresh -> {
                if (!mModel.loading) {
                    loadArticles()
                }
            }
        }
    }

    // Apply new view model
    private fun updateModel() {
        viewState.render(mModel)
    }

    init {
        com.voltek.materialnewsfeed.NewsApp.Companion.presenterComponent.inject(this)

        loadArticles()
    }

    override fun attachView(view: com.voltek.materialnewsfeed.ui.list.ListContract.ListView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: com.voltek.materialnewsfeed.ui.list.ListContract.ListView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    override fun onDestroy() {
        mArticles.unsubscribe()
    }

    private fun loadArticles() {
        mModel.articles.clear()
        mModel.loading = true
        mModel.message = ""
        updateModel()

        mArticles.execute(
                null,
                io.reactivex.functions.Consumer {
                    mModel.addData(it.data)
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
        mArticles.unsubscribe()
        updateModel()
    }
}
