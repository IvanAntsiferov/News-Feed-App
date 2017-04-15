package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.data.api.Source
import com.voltek.materialnewsfeed.mvp.AbstractPresenter
import com.voltek.materialnewsfeed.mvp.BaseView

object NewsSourcesContract {

    interface View : BaseView {

        fun handleLoading()

        fun handleResponse(sources: List<Source>)

        fun handleError(error: String)
    }

    abstract class Presenter : AbstractPresenter<View>()
}
