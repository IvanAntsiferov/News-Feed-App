package com.voltek.materialnewsfeed.ui.news_sources

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.ui.BaseView

object NewsSourcesContract {

    class NewsSourcesModel {
        var categoryId: Int = R.id.action_all
        var loading: Boolean = false
        var sources: ArrayList<Source> = ArrayList()
        var message: String = ""
    }

    interface NewsSourcesView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: NewsSourcesModel)
    }
}
