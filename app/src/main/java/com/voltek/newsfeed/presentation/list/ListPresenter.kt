package com.voltek.newsfeed.presentation.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.domain.interactor.Parameter
import com.voltek.newsfeed.domain.interactor.articles.GetArticlesInteractor
import com.voltek.newsfeed.domain.interactor.news_sources.NewsSourcesUpdatesInteractor
import com.voltek.newsfeed.navigation.command.CommandOpenArticleDetails
import com.voltek.newsfeed.navigation.command.CommandStartActivity
import com.voltek.newsfeed.navigation.proxy.Router
import com.voltek.newsfeed.presentation.Event
import com.voltek.newsfeed.presentation.list.ListContract.ListModel
import com.voltek.newsfeed.presentation.list.ListContract.ListView
import com.voltek.newsfeed.presentation.news_sources.NewsSourcesActivity
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {

    @Inject
    lateinit var mRouter: Router

    @Inject
    lateinit var mArticles: GetArticlesInteractor

    @Inject
    lateinit var mNewsSourcesChanges: NewsSourcesUpdatesInteractor

    // Holds current model through full presenter lifecycle
    private var mModel: ListModel = ListModel()

    // View notify presenter about events using this method
    fun notify(event: Event) {
        when (event) {
            is Event.OpenArticleDetails -> mRouter.execute(CommandOpenArticleDetails(event.article))
            is Event.OpenNewsSources -> mRouter.execute(CommandStartActivity(NewsSourcesActivity()))
            is Event.Refresh -> {
                if (!mModel.loading) {
                    loadArticles()
                }
            }
        }
    }

    // Apply new view model
    private fun updateModel() {
        Timber.d("updateModel")
        viewState.render(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)

        // Listen for enabled news sources changes and reload articles when it happens.
        mNewsSourcesChanges.execute(
                Parameter(),
                Consumer { loadArticles() },
                Consumer {},
                Action {}
        )

        loadArticles()
    }

    override fun attachView(view: ListView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: ListView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    override fun onDestroy() {
        mArticles.unsubscribe()
        mNewsSourcesChanges.unsubscribe()
    }

    private fun loadArticles() {
        mModel.articles.clear()
        mModel.loading = true
        mModel.message = ""
        updateModel()

        mArticles.execute(
                Parameter(),
                Consumer {
                    mModel.addData(it.data)
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
        mArticles.unsubscribe()
        updateModel()
    }
}
