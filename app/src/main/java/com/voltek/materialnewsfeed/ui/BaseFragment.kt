package com.voltek.materialnewsfeed.ui

import com.arellomobile.mvp.MvpAppCompatFragment
import com.junto.boxy.ui.CompositeDisposableComponent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : MvpAppCompatFragment(),
        CompositeDisposableComponent {

    // Holds all disposables with input events subscriptions
    override val mDisposable = CompositeDisposable()
}
