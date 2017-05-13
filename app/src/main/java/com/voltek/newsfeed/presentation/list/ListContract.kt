package com.voltek.newsfeed.presentation.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.newsfeed.domain.entity.ArticleUI
import com.voltek.newsfeed.presentation.BaseView

object ListContract {

    class ListModel {
        var loading: Boolean = false
        var articles: ArrayList<ArticleUI> = ArrayList()
        var message: String = ""

        fun addData(data: List<ArticleUI>?) {
            if (data != null && !data.isEmpty())
                articles.addAll(data)
        }
    }

    interface ListView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: ListModel)
    }
}
