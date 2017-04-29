package com.voltek.materialnewsfeed.ui.details

import android.os.Bundle
import com.voltek.materialnewsfeed.data.entity.Article

class DetailsPresenter(val article: Article) : DetailsContract.Presenter() {

    override fun attach(view: DetailsContract.View, savedInstanceState: Bundle?) {
        super.attach(view, savedInstanceState)

        mView?.showArticle(article)
    }

    override fun onFirstLaunch() {
        //
    }

    override fun onRestore(savedInstanceState: Bundle) {
        //
    }
}
