package com.voltek.materialnewsfeed.interactor

import com.voltek.materialnewsfeed.utils.CompositeDisposableComponent
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
    fun execute(
            parameter: ParameterType?,
            onNextConsumer: Consumer<ResultType>,
            onErrorConsumer: Consumer<Throwable>
    ) {
        buildObservable(parameter)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
                .subscribe(onNextConsumer, onErrorConsumer)
                .bind()
    }

    fun unsubscribe() {
        resetCompositeDisposable()
    }
}
