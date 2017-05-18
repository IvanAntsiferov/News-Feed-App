package com.voltek.newsfeed.presentation

import com.arellomobile.mvp.MvpPresenter

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    abstract fun notify(event: Event)
}
