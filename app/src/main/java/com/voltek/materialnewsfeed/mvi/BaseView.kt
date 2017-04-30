package com.voltek.materialnewsfeed.mvi

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseView<in Model : BaseModel> : MvpView {

    /**
     * Subscribe to view input events in presenter's attachView method
     */
    @StateStrategyType(SkipStrategy::class)
    fun attachInputListeners()

    /**
     * Unsubscribe presenter from input events in detachView method (before call to super)
     */
    @StateStrategyType(SkipStrategy::class)
    fun detachInputListeners()

    /**
     * Render current view state
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(model: Model)
}
