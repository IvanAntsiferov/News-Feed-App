package com.voltek.materialnewsfeed.ui.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.interactor.articles.GetArticlesInteractor
import com.voltek.materialnewsfeed.navigation.command.CommandStartActivity
import com.voltek.materialnewsfeed.navigation.proxy.Router
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.list.ListContract.ListModel
import com.voltek.materialnewsfeed.ui.list.ListContract.ListView
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesActivity
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {

    @Inject
    lateinit var mRouter: Router

    @Inject
    lateinit var mArticles: GetArticlesInteractor

    // Holds current model through full presenter lifecycle
    private var mModel: ListModel = ListModel()

    // Receive input events from view
    private val input: PublishSubject<Event> = PublishSubject.create()

    // Emits new states and update model
    private val output: BehaviorSubject<ListModel> = BehaviorSubject.createDefault(mModel)

    // View notify presenter about events using this method
    fun notify(event: Event) {
        input.onNext(event)
    }

    // Update and apply news view model
    private fun updateModel() {
        output.onNext(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)

        input.subscribe({
            when (it) {
                is Event.OpenArticleDetails -> {
                    mRouter.execute(CommandOpenDetails(it.article))
                }
                is Event.OpenNewsSources -> {
                    mRouter.execute(CommandStartActivity(NewsSourcesActivity()))
                }
                is Event.Refresh -> {
                    if (!mModel.loading) {
                        loadArticles()
                    }
                }
            }
        })

        output.subscribe({
            viewState.render(it)
        })

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

    private fun loadArticles() {
        mModel.articles.clear()
        mModel.loading = true
        mModel.error = ""
        updateModel()

        mArticles.execute(
                null,
                Consumer {
                    mModel.addData(it.articles)
                    mModel.error = it.message
                    updateModel()
                },
                Consumer {
                    mModel.error = it.message ?: ""
                    finishLoading()
                },
                Action {
                    finishLoading()
                })
    }

    private fun finishLoading() {
        mModel.loading = false
        mArticles.unsubscribe()
        updateModel()
    }
}
