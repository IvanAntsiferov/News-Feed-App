package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import com.voltek.materialnewsfeed.ui.list.ArticleModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.net.ConnectivityManager
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.api.Article
import com.voltek.materialnewsfeed.api.NewsApiArticlesResponse
import javax.inject.Inject

class ListPresenter (navigator: ListContract.Navigator) : ListContract.Presenter<ListContract.Model>(navigator) {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mContext: Context

    private var mNewsModel: ListContract.Model = ArticleModel()

    override fun attach(view: ListContract.View) {
        super.attach(view)

        val listClicksDisposable = mView?.onItemClick()
                ?.subscribe({ article ->
                    mNavigator.openDetails(article)
                }, this::handleError)

        mDisposable.add(listClicksDisposable)

        getArticles()
    }

    fun getArticles() {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        // Check if internet connection is available
        if (networkInfo != null && networkInfo.isConnected) {
            val observable = mNewsModel.provideArticles("the-next-web")
            observable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError)
        } else {
            handleError(NoSuchMethodError())
        }
    }

    fun handleResponse(response: NewsApiArticlesResponse) {
        val articles: List<Article> = response.articles

        if (articles.isEmpty()) {
            mView?.handleError("There is no news")
        } else{
            mView?.handleResponse(articles)
        }
    }

    fun handleError(error: Throwable) {
        error.printStackTrace()
        mView?.handleError(error.javaClass.simpleName)
    }
}
