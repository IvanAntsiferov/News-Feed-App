package com.voltek.materialnewsfeed.mvp

interface BasePresenter<in View : BaseView> {

    fun onAttach(view: View)

    fun onDetach()
}
