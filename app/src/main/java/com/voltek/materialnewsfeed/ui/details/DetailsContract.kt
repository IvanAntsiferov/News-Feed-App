package com.voltek.materialnewsfeed.ui.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.ui.BaseView

object DetailsContract {

    class DetailsModel {
        val author: String = ""
        val title: String = ""
        val description: String = ""
        val urlToImage: String = ""
        val publishedAt: String = ""

        val message: String = ""
    }

    interface DetailsView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: DetailsModel)
    }
}
