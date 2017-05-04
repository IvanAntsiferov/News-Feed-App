package com.voltek.materialnewsfeed.ui.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.BaseView

object ListContract {

    class ListModel {
        var loading: Boolean = false
        var articles: ArrayList<Article> = ArrayList()
        var message: String = ""

        fun addData(data: List<Article>?) {
            if (data != null && !data.isEmpty())
                articles.addAll(data)
        }
    }

    interface ListView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: ListModel)
    }
}
