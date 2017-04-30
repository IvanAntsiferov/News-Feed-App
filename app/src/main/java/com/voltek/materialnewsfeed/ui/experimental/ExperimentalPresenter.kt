package com.voltek.materialnewsfeed.ui.experimental

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalEvents
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalModel
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalView
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

@InjectViewState
class ExperimentalPresenter : MvpPresenter<ExperimentalView>() {

    val input: PublishSubject<ExperimentalEvents> =
            PublishSubject.create()

    init {
        viewState.render(ExperimentalModel.Error("Press any button (start state)"))

        input.subscribe({
            Timber.d("input: " + it.javaClass.simpleName)
            viewState.render(ExperimentalModel.Loading())
            when (it) {
                is ExperimentalEvents.ErrorButton -> error()
                is ExperimentalEvents.PotatoButton -> potato()
                is ExperimentalEvents.TomatoButton -> tomato()
                is ExperimentalEvents.FlagButton -> flag()
            }
        })
    }

    override fun attachView(view: ExperimentalView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: ExperimentalView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    private fun error() {
        val handler = Handler()
        handler.postDelayed({
            val random = Random()
            val i = random.nextInt(4)
            when (i) {
                0 -> viewState.render(ExperimentalModel.Error("No internet connection (error 0)"))
                1 -> viewState.render(ExperimentalModel.Error("Request failed (error 1)"))
                2 -> viewState.render(ExperimentalModel.Error("Nothing found (error 2)"))
                3 -> viewState.render(ExperimentalModel.Error("Try again (error 3)"))
                else -> viewState.render(ExperimentalModel.Error("Something went wrong (error common)"))
            }
        }, 2000)
    }

    private fun potato() {
        val handler = Handler()
        handler.postDelayed({
            viewState.render(ExperimentalModel.Potato())
        }, 3000)
    }

    private fun tomato() {
        val handler = Handler()
        handler.postDelayed({
            viewState.render(ExperimentalModel.Tomato())
        }, 5000)
    }

    private fun flag() {
        val handler = Handler()
        handler.postDelayed({
            val random = Random()
            val i = random.nextInt(6)
            when (i) {
                0 -> viewState.render(ExperimentalModel.Flag(R.drawable.ic_australia_24dp, "AUSTRALIA"))
                1 -> viewState.render(ExperimentalModel.Flag(R.drawable.ic_germany_24dp, "GERMANY"))
                2 -> viewState.render(ExperimentalModel.Flag(R.drawable.ic_india_24dp, "INDIA"))
                3 -> viewState.render(ExperimentalModel.Flag(R.drawable.ic_italy_24dp, "ITALY"))
                4 -> viewState.render(ExperimentalModel.Flag(R.drawable.ic_united_kingdom_24dp, "UNITED KINGDOM"))
                5 -> viewState.render(ExperimentalModel.Flag(R.drawable.ic_united_states_24dp, "UNITED STATES"))
                else -> viewState.render(ExperimentalModel.Flag(R.drawable.ic_filter_list_white_24dp, ":("))
            }
        }, 1000)
    }
}
