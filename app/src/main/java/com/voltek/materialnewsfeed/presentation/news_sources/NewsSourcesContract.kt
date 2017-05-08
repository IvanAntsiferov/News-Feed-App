package com.voltek.materialnewsfeed.presentation.news_sources

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.presentation.BaseView

object NewsSourcesContract {

    class NewsSourcesModel {
        var categoryId: Int = com.voltek.materialnewsfeed.R.id.action_all
        var loading: Boolean = false
        var sources: ArrayList<com.voltek.materialnewsfeed.data.entity.Source> = ArrayList()
        var message: String = ""

        fun resetId() {
            categoryId = com.voltek.materialnewsfeed.R.id.action_all
        }
    }

    interface NewsSourcesView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: NewsSourcesModel)
    }
}
