package com.voltek.materialnewsfeed.ui.details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsModel
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

@InjectViewState
class DetailsPresenter(article: Article) : MvpPresenter<DetailsView>() {

    private var mModel: DetailsModel = DetailsModel()

    private val input: PublishSubject<Event> = PublishSubject.create()

    private val output: BehaviorSubject<DetailsModel> = BehaviorSubject.createDefault(mModel)

    fun notify(event: Event) {
        input.onNext(event)
    }

    private fun updateModel() {
        output.onNext(mModel)
    }

    init {
        if (article.isEmpty()) {
            mModel.articleLoaded = false
            updateModel()
        } else {
            mModel.articleLoaded = true
            mModel.author = article.author ?: ""
            mModel.description = article.description ?: ""
            mModel.publishedAt = article.publishedAt ?: ""
            mModel.title = article.title ?: ""
            mModel.urlToImage = article.urlToImage ?: ""
            updateModel()
        }

        input.subscribe({
            when (it) {

            }
        })

        output.subscribe({
            viewState.render(it)
        })
    }

    override fun attachView(view: DetailsView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: DetailsView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }
}
