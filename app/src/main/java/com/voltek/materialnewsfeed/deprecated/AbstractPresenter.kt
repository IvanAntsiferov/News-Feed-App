package com.voltek.materialnewsfeed.deprecated

import android.os.Bundle
import com.voltek.materialnewsfeed.NewsApp
import io.reactivex.disposables.CompositeDisposable

@Deprecated("Migrate to new MVP architecture")
abstract class AbstractPresenter<View : BaseView> : BasePresenter<View> {

    protected val mDisposable = CompositeDisposable()

    protected var mView: View? = null

    protected val mRouter = NewsApp.getRouter()

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
