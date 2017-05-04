package com.voltek.materialnewsfeed.ui.experimental

import android.support.annotation.IdRes
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.materialnewsfeed.ui.BaseView

object ExperimentalContract {

    sealed class ExperimentalModel {

        class Loading : ExperimentalModel()

        class Tomato : ExperimentalModel()

        class Potato : ExperimentalModel()

        class Flag(@IdRes val id: Int, val name: String) : ExperimentalModel()

        class Error(val message: String) : ExperimentalModel()
    }

    sealed class ExperimentalEvent {

        class FlagButton : ExperimentalEvent()

        class TomatoButton : ExperimentalEvent()

        class PotatoButton : ExperimentalEvent()

        class ErrorButton : ExperimentalEvent()
    }


    interface ExperimentalView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: ExperimentalModel)
    }
}
