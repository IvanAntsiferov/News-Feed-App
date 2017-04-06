package com.voltek.materialnewsfeed.mvp

import io.reactivex.disposables.CompositeDisposable

abstract class AbstractPresenter<View : BaseView> : BasePresenter<View> {

    protected val mDisposable = CompositeDisposable()

    protected var mView: View? = null

    override fun attach(view: View) {
        mView = view
    }

    override fun detach() {
        mDisposable.clear()
        mView = null
    }
}
