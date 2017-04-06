package com.voltek.materialnewsfeed.ui.details

import com.voltek.materialnewsfeed.data.api.Article

class DetailsPresenter(val article: Article) : DetailsContract.Presenter() {

    override fun attach(view: DetailsContract.View) {
        super.attach(view)

        mView?.showArticle(article)
    }
}
