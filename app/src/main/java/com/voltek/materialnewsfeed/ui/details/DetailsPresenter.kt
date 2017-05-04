package com.voltek.materialnewsfeed.ui.details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView

@InjectViewState
class DetailsPresenter(article: Article) : MvpPresenter<DetailsView>() {

    init {

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
