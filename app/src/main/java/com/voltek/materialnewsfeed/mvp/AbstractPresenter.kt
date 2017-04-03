package com.voltek.materialnewsfeed.mvp

import io.reactivex.disposables.CompositeDisposable

abstract class AbstractPresenter<out Nav : BaseNavigator, View : BaseView>(nav: Nav) : BasePresenter<View> {

    protected var mView: View? = null

    protected val mNavigator: Nav = nav

    protected val mDisposable = CompositeDisposable()

    override fun attach(view: View) {
        mView = view
    }

    override fun detach() {
        mDisposable.clear()
        mView = null
    }
}
