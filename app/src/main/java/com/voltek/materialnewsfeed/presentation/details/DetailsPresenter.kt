package com.voltek.materialnewsfeed.presentation.details

import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsModel
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView

@com.arellomobile.mvp.InjectViewState
class DetailsPresenter(private val article: com.voltek.materialnewsfeed.data.entity.Article) : com.arellomobile.mvp.MvpPresenter<DetailsView>() {

    @javax.inject.Inject
    lateinit var mRouter: com.voltek.materialnewsfeed.navigation.proxy.Router

    private var mModel: com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsModel = com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsModel()

    fun notify(event: com.voltek.materialnewsfeed.ui.Event) {
        when (event) {
            is com.voltek.materialnewsfeed.ui.Event.Share -> {
                if (!article.isEmpty()) {
                    mRouter.execute(com.voltek.materialnewsfeed.ui.details.CommandShareArticle(article.title ?: "", article.url ?: ""))
                }
            }
            is com.voltek.materialnewsfeed.ui.Event.OpenWebsite -> mRouter.execute(com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite(article.url ?: ""))
        }
    }

    private fun updateModel() {
        viewState.render(mModel)
    }

    init {
        com.voltek.materialnewsfeed.NewsApp.Companion.presenterComponent.inject(this)

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

    override fun attachView(view: com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }
}
