package com.voltek.newsfeed.presentation.news_sources

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.newsfeed.R
import com.voltek.newsfeed.domain.entity.SourceUI
import com.voltek.newsfeed.presentation.BaseModel
import com.voltek.newsfeed.presentation.BaseView

object NewsSourcesContract {

    class NewsSourcesModel(subscriber: (BaseModel) -> Unit) : BaseModel(subscriber) {
        var categoryId: Int = R.id.action_all
        var loading: Boolean = false
        var sources: ArrayList<SourceUI> = ArrayList()
        var message: String = ""

        fun resetId() {
            categoryId = R.id.action_all
        }
    }

    interface NewsSourcesView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: NewsSourcesModel)
    }
}
