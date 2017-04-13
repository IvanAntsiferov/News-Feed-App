package com.voltek.materialnewsfeed.ui.news_sources

import android.os.Bundle

class NewsSourcesPresenter : NewsSourcesContract.Presenter() {

    override fun attach(view: NewsSourcesContract.View, savedInstanceState: Bundle?) {
        super.attach(view, savedInstanceState)
    }

    override fun onFirstLaunch() {
        //
    }

    override fun onRestore(savedInstanceState: Bundle?) {
        //
    }
}
