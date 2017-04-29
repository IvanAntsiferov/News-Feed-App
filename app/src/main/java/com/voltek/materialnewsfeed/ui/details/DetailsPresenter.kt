package com.voltek.materialnewsfeed.ui.details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.data.entity.Article

@InjectViewState
class DetailsPresenter(val article: Article) : MvpPresenter<DetailsView>() {

    init {
        if (article.isEmpty())
            viewState.render(DetailsModel.Error())
        else
            viewState.render(
                    DetailsModel.Loaded(
                            article.author!!,
                            article.title!!,
                            article.description!!,
                            article.urlToImage!!,
                            article.publishedAt!!
                    )
            )
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
