package com.voltek.newsfeed.presentation.splash

import com.arellomobile.mvp.presenter.InjectPresenter
import com.voltek.newsfeed.navigation.command.CommandStartActivity
import com.voltek.newsfeed.navigation.proxy.Command
import com.voltek.newsfeed.presentation.BaseActivity

class SplashActivity : BaseActivity(),
        SplashView {

    @InjectPresenter
    lateinit var mPresenter: SplashPresenter

    override fun executeCommand(command: Command): Boolean = when (command) {
        is CommandStartActivity -> startActivity(command)
        else -> false
    }

    override fun attachInputListeners() {}

    override fun detachInputListeners() {}
}
