package com.voltek.materialnewsfeed.ui.list

import android.view.MenuItem
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.mvp.AbstractNavigablePresenter
import com.voltek.materialnewsfeed.mvp.BaseNavigator
import com.voltek.materialnewsfeed.mvp.BaseView
import io.reactivex.Observable

object ListContract {

    interface Navigator : BaseNavigator {

        fun toolbarClicks(): Observable<MenuItem>

        fun openDetails(article: Article)

        fun openNewsSources()

        fun isDualPane(): Boolean
    }

    interface View : BaseView {

        fun onSwipeToRefresh(): Observable<Unit>

        fun onItemClick(): Observable<Article>

        fun handleLoading()

        fun handleResponse(articles: List<Article>)

        fun handleError(message: String)
    }

    abstract class Presenter(nav: Navigator) : AbstractNavigablePresenter<Navigator, View>(nav)
}
