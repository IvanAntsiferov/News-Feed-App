package com.voltek.materialnewsfeed.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseView : MvpView {

    // Subscribe presenter to input events
    @StateStrategyType(SkipStrategy::class)
    fun attachInputListeners()

    // Unsubscribe presenter from input events
    @StateStrategyType(SkipStrategy::class)
    fun detachInputListeners()
}
