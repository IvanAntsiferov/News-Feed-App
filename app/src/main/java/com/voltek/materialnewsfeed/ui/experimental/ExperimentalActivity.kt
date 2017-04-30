package com.voltek.materialnewsfeed.ui.experimental

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.mvi.BaseView
import com.voltek.materialnewsfeed.navigation.proxy.Command
import com.voltek.materialnewsfeed.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_experimental.*

class ExperimentalActivity : BaseActivity(),
        BaseView<ExperimentalModel> {

    companion object {
        const val TAG = "ExperimentalActivity"
    }

    @InjectPresenter(type = PresenterType.LOCAL, tag = TAG)
    lateinit var mPresenter: ExperimentalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experimental)
    }

    override fun executeCommand(command: Command): Boolean {
        return false
    }

    override fun attachInputListeners() {


        mDisposable.addAll()
    }

    override fun detachInputListeners() {
        mDisposable.clear()
    }

    override fun render(model: ExperimentalModel) {
        when (model) {
            is ExperimentalModel.Loading -> {
                progress_bar.visibility = VISIBLE
                tv_error.text = ""
                tv_state.text = ""
            }
            is ExperimentalModel.Error -> {
                progress_bar.visibility = GONE
                tv_error.text = model.message
                tv_state.text = ""
            }
            is ExperimentalModel.Potato -> {
                progress_bar.visibility = GONE
                tv_error.text = ""
                tv_state.text = "POTATO"
            }
            is ExperimentalModel.Tomato -> {
                progress_bar.visibility = GONE
                tv_error.text = ""
                tv_state.text = "TOMATO"
            }
        }
    }
}
