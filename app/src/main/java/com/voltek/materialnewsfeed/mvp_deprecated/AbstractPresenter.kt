package com.voltek.materialnewsfeed.mvp_deprecated

import android.os.Bundle
import com.voltek.materialnewsfeed.NewsApp
import io.reactivex.disposables.CompositeDisposable

@Deprecated("Move to Mosby MVI architecture")
abstract class AbstractPresenter<View : BaseView> : BasePresenter<View> {

    protected val mDisposable = CompositeDisposable()

    protected var mView: View? = null

    protected val mRouter = NewsApp.getRouterBus()

    override fun attach(view: View, savedInstanceState: Bundle?) {
        mView = view

        if (savedInstanceState == null) {
            onFirstLaunch()
        } else {
            onRestore(savedInstanceState)
        }
    }

    override fun detach() {
        mDisposable.clear()
        mView = null
    }

    abstract fun onFirstLaunch()

    abstract fun onRestore(savedInstanceState: Bundle)
}
