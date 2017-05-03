package com.voltek.materialnewsfeed.ui.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.interactor.articles.GetArticlesInteractor
import com.voltek.materialnewsfeed.ui.list.ListContract.ListEvent
import com.voltek.materialnewsfeed.ui.list.ListContract.ListModel
import com.voltek.materialnewsfeed.ui.list.ListContract.ListView
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {

    @Inject
    lateinit var mArticles: GetArticlesInteractor

    // Holds current model through full presenter lifecycle
    private var mModel: ListModel? = null

    // Receive input events from view
    private val input: PublishSubject<ListEvent> = PublishSubject.create()

    // Emits new states and update model
    private val output: BehaviorSubject<ListModel> = BehaviorSubject.create()//Default(mModel)

    fun event(event: ListEvent) {
        input.onNext(event)
    }

    private fun updateModel(model: ListModel) {
        mModel = model
        output.onNext(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)

        input.subscribe({
            when (it) {

            }
        })

        output.subscribe({
            viewState.render(it)
        })
    }

    override fun attachView(view: ListView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: ListView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }
}
