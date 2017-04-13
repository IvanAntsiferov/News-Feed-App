package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.mvp.AbstractNavigablePresenter
import com.voltek.materialnewsfeed.mvp.BaseNavigator
import com.voltek.materialnewsfeed.mvp.BaseView

object NewsSourcesContract {

    interface Navigator : BaseNavigator

    interface View : BaseView

    abstract class Presenter(nav: Navigator) : AbstractNavigablePresenter<Navigator, View>(nav)
}
