package com.voltek.materialnewsfeed.ui.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.BaseEvent
import com.voltek.materialnewsfeed.ui.BaseModel
import com.voltek.materialnewsfeed.ui.BaseView

object ListContract {

    class ListModel : BaseModel {
        var showLoading: Boolean = false
        var data: ArrayList<Article> = ArrayList()
        var error: String = ""
    }

    /*sealed class ListModel : BaseModel {

        class Loading : ListModel()

        class ResultLoading(val data: List<Article>) : ListModel()

        class Result(val data: List<Article>) : ListModel()

        class ErrorNoData(val error: String) : ListModel()

        class ErrorWithData(val error: String, val data: List<Article>) : ListModel()
    }*/

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
