package com.voltek.materialnewsfeed.ui.details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite
import com.voltek.materialnewsfeed.navigation.proxy.Router
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsModel
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView
import javax.inject.Inject

@InjectViewState
class DetailsPresenter(private val article: Article) : MvpPresenter<DetailsView>() {

    @Inject
    lateinit var mRouter: Router

    private var mModel: DetailsModel = DetailsModel()

    fun notify(event: Event) {
        when (event) {
            is Event.Share -> {
                if (!article.isEmpty()) {
                    mRouter.execute(CommandShareArticle(article.title ?: "", article.url ?: ""))
                }
            }
            is Event.OpenWebsite -> mRouter.execute(CommandOpenWebsite(article.url ?: ""))
        }
    }

    private fun updateModel() {
        viewState.render(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)

        if (article.isEmpty()) {
            mModel.articleLoaded = false
            updateModel()
        } else {
            mModel.articleLoaded = true
            mModel.author = article.author ?: ""
            mModel.description = article.description ?: ""
            mModel.publishedAt = article.publishedAt ?: ""
            mModel.title = article.title ?: ""
            mModel.urlToImage = article.urlToImage ?: ""
            updateModel()
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
