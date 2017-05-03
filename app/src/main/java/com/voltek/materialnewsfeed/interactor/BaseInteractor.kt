package com.voltek.materialnewsfeed.interactor

import com.voltek.materialnewsfeed.mvi.CompositeDisposableComponent
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

abstract class BaseInteractor<ResultType, in ParameterType>(
        protected val jobScheduler: Scheduler,
        protected val uiScheduler: Scheduler
) : CompositeDisposableComponent {

    override val mDisposable = CompositeDisposable()

    protected abstract fun buildObservable(parameter: ParameterType?): Observable<ResultType>

    /**
     * @param parameter pass null, if parameter does not needed
     */
    fun execute(parameter: ParameterType?, consumer: Consumer<ResultType>) {
        buildObservable(parameter)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
                .subscribe(consumer)
                .bind()
    }

    fun unsubscribe() {
        resetCompositeDisposable()
    }
}
