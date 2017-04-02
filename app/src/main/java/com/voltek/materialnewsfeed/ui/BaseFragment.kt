package com.voltek.materialnewsfeed.ui

import android.support.v4.app.Fragment
import com.voltek.materialnewsfeed.mvp.BasePresenter
import com.voltek.materialnewsfeed.mvp.BaseView

abstract class BaseFragment<in View : BaseView, Presenter : BasePresenter<View>> : Fragment() {

    var mPresenter: Presenter? = null

    override fun onDestroyView() {
        mPresenter?.detach()
        super.onDestroyView()
    }
}
