package com.voltek.materialnewsfeed.deprecated

import android.os.Bundle

@Deprecated("Migrate to new MVP architecture")
interface BasePresenter<in View : BaseView> {

    fun attach(view: View, savedInstanceState: Bundle?)

    fun detach()
}
