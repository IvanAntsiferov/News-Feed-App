package com.voltek.materialnewsfeed.ui.details

import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.mvp.AbstractPresenter
import com.voltek.materialnewsfeed.mvp.BaseNavigator
import com.voltek.materialnewsfeed.mvp.BaseView

object DetailsContract {

    interface View : BaseView {

        fun showArticle(article: Article)
    }

    abstract class Presenter : AbstractPresenter<View>()
}
