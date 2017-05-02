package com.voltek.materialnewsfeed.ui.experimental

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalEvents
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalModel
import com.voltek.materialnewsfeed.ui.experimental.ExperimentalContract.ExperimentalView
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

@InjectViewState
class ExperimentalPresenter : MvpPresenter<ExperimentalView>() {

    // Holds current model through full presenter lifecycle
    private var mModel: ExperimentalModel = ExperimentalModel.Error("start state")

    // Receive input events from view
    val input: PublishSubject<ExperimentalEvents> = PublishSubject.create()

    // Emit new states and update model
    private val output: BehaviorSubject<ExperimentalModel> = BehaviorSubject.createDefault(mModel)

    private fun updateModel(model: ExperimentalModel) {
        mModel = model
        output.onNext(mModel)
    }

    init {
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
        output.subscribe({
            viewState.render(it)
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
                0 -> updateModel(ExperimentalModel.Error("No internet connection (error 0)"))
                1 -> updateModel(ExperimentalModel.Error("Request failed (error 1)"))
                2 -> updateModel(ExperimentalModel.Error("Nothing found (error 2)"))
                3 -> updateModel(ExperimentalModel.Error("Try again (error 3)"))
                else -> updateModel(ExperimentalModel.Error("Something went wrong (error common)"))
            }
        }, 2000)
    }

    private fun potato() {
        val handler = Handler()
        handler.postDelayed({
            updateModel(ExperimentalModel.Potato())
        }, 3000)
    }

    private fun tomato() {
        val handler = Handler()
        handler.postDelayed({
            updateModel(ExperimentalModel.Tomato())
        }, 5000)
    }

    private fun flag() {
        val handler = Handler()
        handler.postDelayed({
            val random = Random()
            val i = random.nextInt(6)
            when (i) {
                0 -> updateModel(ExperimentalModel.Flag(R.drawable.ic_australia_24dp, "AUSTRALIA"))
                1 -> updateModel(ExperimentalModel.Flag(R.drawable.ic_germany_24dp, "GERMANY"))
                2 -> updateModel(ExperimentalModel.Flag(R.drawable.ic_india_24dp, "INDIA"))
                3 -> updateModel(ExperimentalModel.Flag(R.drawable.ic_italy_24dp, "ITALY"))
                4 -> updateModel(ExperimentalModel.Flag(R.drawable.ic_united_kingdom_24dp, "UNITED KINGDOM"))
                5 -> updateModel(ExperimentalModel.Flag(R.drawable.ic_united_states_24dp, "UNITED STATES"))
                else -> updateModel(ExperimentalModel.Flag(R.drawable.ic_filter_list_white_24dp, ":("))
            }
        }, 1000)
    }
}
