package com.voltek.materialnewsfeed.ui.experimental

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.mvi.BaseView

@InjectViewState
class ExperimentalPresenter : MvpPresenter<BaseView<ExperimentalModel>>() {

    private var mModel: ExperimentalModel = ExperimentalModel.Loading()

    init {
        viewState.render(mModel)
    }

    override fun attachView(view: BaseView<ExperimentalModel>?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: BaseView<ExperimentalModel>?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }
}
