package com.voltek.materialnewsfeed.Utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface CompositeDisposableComponent {

    /**
     * Holds Disposables
     */
    val mDisposable: CompositeDisposable

    /**
     * Easy add disposables to composite with this function
     */
    fun Disposable.bind() = mDisposable.add(this)

    /**
     * Unsubscribe when subscriptions no longer needed
     */
    fun resetCompositeDisposable() {
        mDisposable.clear()
    }
}
