package com.voltek.materialnewsfeed.deprecated

import android.support.v4.app.Fragment

@Deprecated("Migrate to new MVP architecture")
abstract class BaseFragment<in View : BaseView, Presenter : BasePresenter<View>> : Fragment() {

    protected var mPresenter: Presenter? = null

    init {
        retainInstance = true
    }

    override fun onDestroyView() {
        mPresenter?.detach()
        super.onDestroyView()
    }
}
