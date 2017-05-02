package com.junto.boxy.ui

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface CompositeDisposableComponent {

    /**
     * Holds Disposables of input events
     */
    val mDisposable: CompositeDisposable

    /**
     * Easy add disposables to composite with this function
     */
    fun Disposable.bind() = mDisposable.add(this)

    /**
     * Unsubscribe presenter from input events
     */
    fun resetCompositeDisposable() {
        mDisposable.clear()
    }
}
