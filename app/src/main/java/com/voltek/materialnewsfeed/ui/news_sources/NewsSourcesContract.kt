package com.voltek.materialnewsfeed.ui.news_sources

import android.view.MenuItem
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.deprecated.AbstractPresenter
import com.voltek.materialnewsfeed.deprecated.BaseView
import io.reactivex.Observable

object NewsSourcesContract {

    interface View : BaseView {

        fun toolbarClicks(): Observable<MenuItem>

        fun filter(category: String)

        fun handleLoading()

        fun handleResponse(sources: List<Source>)

        fun handleError(error: String)
    }

    abstract class Presenter : AbstractPresenter<View>()
}
