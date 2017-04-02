package com.voltek.materialnewsfeed.mvp

interface BasePresenter<in View : BaseView> {

    fun attach(view: View)

    fun detach()
}
