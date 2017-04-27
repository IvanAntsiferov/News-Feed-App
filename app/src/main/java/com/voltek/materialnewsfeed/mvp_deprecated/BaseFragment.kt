package com.voltek.materialnewsfeed.mvp_deprecated

import android.support.v4.app.Fragment
import com.voltek.materialnewsfeed.mvp_deprecated.BasePresenter
import com.voltek.materialnewsfeed.mvp_deprecated.BaseView

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
