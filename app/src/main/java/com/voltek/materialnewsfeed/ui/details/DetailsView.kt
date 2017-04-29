package com.voltek.materialnewsfeed.ui.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.ui.BaseView

interface DetailsView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(model: DetailsModel)
}
