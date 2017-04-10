package com.voltek.materialnewsfeed.mvp

import android.os.Bundle

interface BasePresenter<in View : BaseView> {

    fun attach(view: View, savedInstanceState: Bundle?)

    fun detach()
}
