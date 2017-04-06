package com.voltek.materialnewsfeed.mvp

abstract class AbstractNavigablePresenter<out Nav : BaseNavigator, View : BaseView>(nav: Nav) :
        AbstractPresenter<View>() {

    protected val mNavigator: Nav = nav
}
