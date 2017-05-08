package com.voltek.materialnewsfeed.presentation.details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite
import com.voltek.materialnewsfeed.presentation.Event
import com.voltek.materialnewsfeed.presentation.details.DetailsContract.DetailsModel
import com.voltek.materialnewsfeed.presentation.details.DetailsContract.DetailsView

@InjectViewState
class DetailsPresenter(private val article: Article) : MvpPresenter<DetailsView>() {

    @javax.inject.Inject
    lateinit var mRouter: com.voltek.materialnewsfeed.navigation.proxy.Router

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

    override fun attachView(view: DetailsView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: DetailsView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }
}
