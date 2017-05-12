package com.voltek.newsfeed.presentation.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.navigation.command.CommandStartActivity
import com.voltek.newsfeed.navigation.proxy.Router
import com.voltek.newsfeed.presentation.list.ListActivity
import javax.inject.Inject

@InjectViewState
class SplashPresenter : MvpPresenter<SplashView>() {

    @Inject
    lateinit var mRouter: Router

    init {
        NewsApp.presenterComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        // TODO check if this is first launch and start onboarding
        mRouter.execute(CommandStartActivity(ListActivity(), finish = true))
    }
}
