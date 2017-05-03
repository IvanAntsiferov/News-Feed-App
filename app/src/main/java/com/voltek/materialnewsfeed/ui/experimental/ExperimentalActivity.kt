package com.voltek.materialnewsfeed.ui.experimental

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.navigation.proxy.Command
import com.voltek.materialnewsfeed.ui.BaseActivity
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalEvent
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalModel
import kotlinx.android.synthetic.main.activity_experimental.*
import timber.log.Timber

class ExperimentalActivity : BaseActivity(),
        ExperimentalContract.ExperimentalView {

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
        // Button potato click
        RxView.clicks(btn_potato)
                .subscribe({
                    mPresenter.event(ExperimentalEvent.PotatoButton())
                })
                .bind()

        // Button tomato click
        RxView.clicks(btn_tomato)
                .subscribe({
                    mPresenter.event(ExperimentalEvent.TomatoButton())
                })
                .bind()

        // Button error click
        RxView.clicks(btn_error)
                .subscribe({
                    mPresenter.event(ExperimentalEvent.ErrorButton())
                })
                .bind()

        // Button flag click
        RxView.clicks(btn_flag)
                .subscribe({
                    mPresenter.event(ExperimentalEvent.FlagButton())
                })
                .bind()
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: ExperimentalModel) {
        Timber.d("render: " + model.javaClass.simpleName)
        when (model) {
            is ExperimentalModel.Loading -> {
                im_image.visibility = GONE

                progress_bar.visibility = VISIBLE

                tv_error.text = ""

                tv_state.text = ""
            }
            is ExperimentalModel.Error -> {
                im_image.visibility = GONE

                progress_bar.visibility = GONE

                tv_error.text = model.message

                tv_state.text = ""
            }
            is ExperimentalModel.Potato -> {
                im_image.visibility = GONE

                progress_bar.visibility = GONE

                tv_error.text = ""

                tv_state.text = "POTATO"
            }
            is ExperimentalModel.Tomato -> {
                im_image.visibility = GONE

                progress_bar.visibility = GONE

                tv_error.text = ""

                tv_state.text = "TOMATO"
            }
            is ExperimentalModel.Flag -> {
                Glide.with(this).load("").error(model.id).into(im_image)
                im_image.visibility = VISIBLE

                progress_bar.visibility = GONE

                tv_error.text = ""

                tv_state.text = model.name
            }
        }
    }
}
