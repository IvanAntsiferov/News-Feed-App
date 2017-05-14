package com.voltek.newsfeed.presentation.details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.domain.entity.ArticleUI
import com.voltek.newsfeed.navigation.command.CommandOpenWebsite
import com.voltek.newsfeed.navigation.command.CommandShareArticle
import com.voltek.newsfeed.navigation.command.CommandStartActivity
import com.voltek.newsfeed.navigation.proxy.Router
import com.voltek.newsfeed.presentation.Event
import com.voltek.newsfeed.presentation.details.DetailsContract.DetailsModel
import com.voltek.newsfeed.presentation.details.DetailsContract.DetailsView
import com.voltek.newsfeed.presentation.news_sources.NewsSourcesActivity
import javax.inject.Inject

@InjectViewState
class DetailsPresenter(private val article: ArticleUI) : MvpPresenter<DetailsView>() {

    @Inject
    lateinit var mRouter: Router

    private var mModel: DetailsModel = DetailsModel { viewState.render(it as DetailsModel) }

    fun notify(event: Event) {
        when (event) {
            is Event.Share -> {
                if (!article.isEmpty()) {
                    mRouter.execute(CommandShareArticle(article.title ?: "", article.url ?: ""))
                }
            }
            is Event.OpenWebsite -> mRouter.execute(CommandOpenWebsite(article.url ?: ""))
            is Event.OpenNewsSources -> mRouter.execute(CommandStartActivity(NewsSourcesActivity()))
        }
    }

    init {
        NewsApp.presenterComponent.inject(this)

        if (article.isEmpty()) {
            mModel.articleLoaded = false
            mModel.update()
        } else {
            mModel.articleLoaded = true
            mModel.description = article.description ?: ""
            mModel.title = article.title ?: ""
            mModel.urlToImage = article.urlToImage ?: ""
            mModel.update()
        }
    }

    override fun attachView(view: DetailsView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: DetailsView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }
}
