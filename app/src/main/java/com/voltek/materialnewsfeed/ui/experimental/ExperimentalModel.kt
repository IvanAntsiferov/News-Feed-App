package com.voltek.materialnewsfeed.ui.experimental

import com.voltek.materialnewsfeed.mvi.BaseModel

sealed class ExperimentalModel : BaseModel {

    class Loading : ExperimentalModel()

    class Tomato : ExperimentalModel()

    class Potato : ExperimentalModel()

    class Error(val message: String) : ExperimentalModel()
}
