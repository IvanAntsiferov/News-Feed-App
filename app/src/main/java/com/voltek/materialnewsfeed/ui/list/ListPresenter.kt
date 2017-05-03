package com.voltek.materialnewsfeed.ui.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.interactor.articles.GetArticlesInteractor
import com.voltek.materialnewsfeed.navigation.command.CommandStartActivity
import com.voltek.materialnewsfeed.navigation.proxy.Router
import com.voltek.materialnewsfeed.ui.list.ListContract.ListEvent
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
    lateinit var mArticles: GetArticlesInteractor

    private val mRouter: Router = NewsApp.getRouter()

    // Holds current model through full presenter lifecycle
    private var mModel: ListModel = ListModel()

    // Receive input events from view
    private val input: PublishSubject<ListEvent> = PublishSubject.create()

    // Emits new states and update model
    private val output: BehaviorSubject<ListModel> = BehaviorSubject.createDefault(mModel)

    // View notify presenter about events using this method
    fun event(event: ListEvent) {
        input.onNext(event)
    }

    // Update and apply view model
    private fun updateModel(model: ListModel) {
        mModel = model
        output.onNext(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)

        input.subscribe({
            when (it) {
                is ListEvent.OpenDetails -> {
                    mRouter.execute(CommandOpenDetails(it.article))
                }
                is ListEvent.OpenNewsSources -> {
                    mRouter.execute(CommandStartActivity(NewsSourcesActivity()))
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
        mArticles.execute(
                null,
                Consumer {

                },
                Consumer {

                },
                Action {

                })
    }
}
