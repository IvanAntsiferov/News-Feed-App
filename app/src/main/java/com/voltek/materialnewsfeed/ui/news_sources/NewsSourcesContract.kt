package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.mvp.AbstractPresenter
import com.voltek.materialnewsfeed.mvp.BaseView

object NewsSourcesContract {

    interface View : BaseView

    abstract class Presenter : AbstractPresenter<View>()
}
