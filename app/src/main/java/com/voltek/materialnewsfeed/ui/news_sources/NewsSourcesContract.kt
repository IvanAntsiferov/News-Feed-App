package com.voltek.materialnewsfeed.ui.news_sources

import android.view.MenuItem
import com.voltek.materialnewsfeed.data.api.Source
import com.voltek.materialnewsfeed.mvp.AbstractNavigablePresenter
import com.voltek.materialnewsfeed.mvp.BaseNavigator
import com.voltek.materialnewsfeed.mvp.BaseView
import io.reactivex.Observable

object NewsSourcesContract {

    interface Navigator : BaseNavigator {

        fun toolbarClicks(): Observable<MenuItem>
    }

    interface View : BaseView {

        fun handleLoading()

        fun handleResponse(sources: List<Source>)

        fun handleError(error: String)
    }

    abstract class Presenter(nav: Navigator) : AbstractNavigablePresenter<Navigator, View>(nav)
}
