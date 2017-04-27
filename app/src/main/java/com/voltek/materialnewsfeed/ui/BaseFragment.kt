package com.voltek.materialnewsfeed.ui

import com.arellomobile.mvp.MvpAppCompatFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : MvpAppCompatFragment() {

    // Holds all disposables with input events subscriptions
    protected val mDisposable = CompositeDisposable()
}
