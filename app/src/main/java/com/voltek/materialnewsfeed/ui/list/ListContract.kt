package com.voltek.materialnewsfeed.ui.list

import android.view.View
import com.voltek.materialnewsfeed.api.Article
import com.voltek.materialnewsfeed.mvp.AbstractPresenter
import com.voltek.materialnewsfeed.mvp.BaseNavigator
import com.voltek.materialnewsfeed.mvp.BaseView
import io.reactivex.Observable

object ListContract {

    interface Navigator : BaseNavigator {

        fun openDetails(article: Article)
    }

    interface View : BaseView {

        fun onItemClick(): Observable<Article>

        fun handleResponse(articles: List<Article>)

        fun handleError(error: String)
    }

    abstract class Presenter(nav: Navigator) : AbstractPresenter<Navigator, View>(nav)
}
