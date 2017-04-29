package com.voltek.materialnewsfeed.ui.news_sources

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.ui.BaseView

interface NewsSourcesView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(model: NewsSourcesModel)
}
