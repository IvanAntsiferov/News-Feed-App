package com.voltek.newsfeed.presentation.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.data.exception.NoNewsSourcesSelectedException
import com.voltek.newsfeed.domain.entity.SourceUI
import com.voltek.newsfeed.domain.interactor.Parameter
import com.voltek.newsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import com.voltek.newsfeed.navigation.command.CommandStartActivity
import com.voltek.newsfeed.navigation.command.CommandSystemMessage
import com.voltek.newsfeed.navigation.proxy.Router
import com.voltek.newsfeed.presentation.list.ListActivity
import com.voltek.newsfeed.presentation.news_sources.NewsSourcesActivity
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject

@InjectViewState
class SplashPresenter : MvpPresenter<SplashView>() {

    @Inject
    lateinit var mRouter: Router

    @Inject
    lateinit var mNewsSources: NewsSourcesInteractor

    init {
        NewsApp.presenterComponent.inject(this)

        // Fetch news sources in background.
        // Check, if there is no enabled news sources, open NewsSources screen with proper message.
        mNewsSources.execute(
                Parameter(NewsSourcesInteractor.GET),
                Consumer {
                    result(hasEnabled(it?.data ?: ArrayList()))
                },
                Consumer {
                    it.printStackTrace()
                    result(false)
                },
                Action {}
        )
    }

    override fun onDestroy() {
        mNewsSources.unsubscribe()
    }

    private fun result(hasEnabled: Boolean) {
        mRouter.execute(CommandStartActivity(ListActivity(), finish = true))

        if (!hasEnabled) {
            mRouter.execute(CommandStartActivity(NewsSourcesActivity(), finish = true))
            mRouter.execute(CommandSystemMessage(error = NoNewsSourcesSelectedException()))
        }
    }

    private fun hasEnabled(sources: List<SourceUI>): Boolean = sources.any { it.isEnabled }
}
