package com.voltek.materialnewsfeed.ui.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.ui.BaseView

object DetailsContract {

    class DetailsModel {
        var articleLoaded = false

        var author: String = ""
        var title: String = ""
        var description: String = ""
        var urlToImage: String = ""
        var publishedAt: String = ""
    }

    interface DetailsView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: DetailsModel)
    }
}
