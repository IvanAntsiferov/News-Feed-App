package com.voltek.materialnewsfeed.ui.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.ui.BaseEvent
import com.voltek.materialnewsfeed.ui.BaseModel
import com.voltek.materialnewsfeed.ui.BaseView

object ListContract {

    sealed class ListModel : BaseModel {

        //
    }

    sealed class ListEvent : BaseEvent {

        //
    }

    interface ListView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: ListModel)
    }
}
