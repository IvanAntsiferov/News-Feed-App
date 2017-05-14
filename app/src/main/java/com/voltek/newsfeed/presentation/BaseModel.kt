package com.voltek.newsfeed.presentation

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * Base View Model class.
 *
 * @param subscriber function, that will be called when model updates
 */
abstract class BaseModel(subscriber: (BaseModel) -> Unit) {

    private val mUpdateFeed: PublishSubject<Unit> = PublishSubject.create()

    private val mSubscription: Disposable

    init {
        mSubscription = mUpdateFeed.subscribe({ subscriber(this@BaseModel) })
    }

    /**
     * Call this method to render new model state
     */
    fun update() {
        mUpdateFeed.onNext(Unit)
    }

    /**
     * Call this in presenter onDestroy() to clear Rx subscriptions
     */
    fun destroy() {
        mSubscription.dispose()
    }
}
