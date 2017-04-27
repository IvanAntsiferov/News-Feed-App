package com.voltek.materialnewsfeed.ui.details

import com.voltek.materialnewsfeed.data.networking.Article
import com.voltek.materialnewsfeed.deprecated.AbstractPresenter
import com.voltek.materialnewsfeed.deprecated.BaseView

object DetailsContract {

    interface View : BaseView {

        fun showArticle(article: Article)
    }

    abstract class Presenter : AbstractPresenter<View>()
}
