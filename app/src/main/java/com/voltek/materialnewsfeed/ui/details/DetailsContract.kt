package com.voltek.materialnewsfeed.ui.details

import com.voltek.materialnewsfeed.mvp.AbstractPresenter
import com.voltek.materialnewsfeed.mvp.BaseNavigator
import com.voltek.materialnewsfeed.mvp.BaseView

object DetailsContract {

    interface Navigator : BaseNavigator {

        //
    }

    interface View : BaseView {

        //
    }

    abstract class Presenter(nav: Navigator) : AbstractPresenter<Navigator, View>(nav) {

        //
    }
}
