package com.voltek.materialnewsfeed.ui.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.BaseEvent
import com.voltek.materialnewsfeed.ui.BaseModel
import com.voltek.materialnewsfeed.ui.BaseView

object ListContract {

    class ListModel : BaseModel {
        var loading: Boolean = false
        var articles: ArrayList<Article> = ArrayList()
        var error: String = ""

        fun addData(data: List<Article>) {
            articles.addAll(data)
        }
    }

    // TODO make independent events system
    sealed class ListEvent : BaseEvent {

        class OpenDetails(val article: Article) : ListEvent()

        class OpenNewsSources : ListEvent()

        class SwipeToRefresh : ListEvent()
    }

    interface ListView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: ListModel)
    }
}
