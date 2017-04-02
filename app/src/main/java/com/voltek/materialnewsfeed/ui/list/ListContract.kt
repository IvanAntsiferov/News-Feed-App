package com.voltek.materialnewsfeed.ui.list

import com.voltek.materialnewsfeed.api.Article
import com.voltek.materialnewsfeed.mvp.AbstractPresenter
import com.voltek.materialnewsfeed.mvp.BaseNavigator
import com.voltek.materialnewsfeed.mvp.BaseView

object ListContract {

    interface Navigator : BaseNavigator {

        //
    }

    interface View : BaseView {

        fun handleResponse(articles: List<Article>)

        fun handleError(error: String)
    }

    abstract class Presenter(nav: Navigator) : AbstractPresenter<Navigator, View>(nav) {

        //
    }
}
