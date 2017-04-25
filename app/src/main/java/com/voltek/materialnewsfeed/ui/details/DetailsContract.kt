package com.voltek.materialnewsfeed.ui.details

import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.mvp_deprecated.AbstractPresenter
import com.voltek.materialnewsfeed.mvp_deprecated.BaseView

object DetailsContract {

    interface View : BaseView {

        fun showArticle(article: Article)
    }

    abstract class Presenter : AbstractPresenter<View>()
}
