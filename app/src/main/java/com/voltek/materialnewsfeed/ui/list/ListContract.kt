package com.voltek.materialnewsfeed.ui.list

import android.view.MenuItem
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.mvp_deprecated.AbstractPresenter
import com.voltek.materialnewsfeed.mvp_deprecated.BaseView
import io.reactivex.Observable

object ListContract {

    interface View : BaseView {

        fun toolbarClicks(): Observable<MenuItem>

        fun onSwipeToRefresh(): Observable<Unit>

        fun onItemClick(): Observable<Article>

        fun handleLoading()

        fun handleResponse(articles: List<Article>)

        fun handleError(message: String)
    }

    abstract class Presenter : AbstractPresenter<View>()
}
