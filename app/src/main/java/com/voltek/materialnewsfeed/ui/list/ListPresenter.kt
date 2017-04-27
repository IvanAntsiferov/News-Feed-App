package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.os.Bundle
import android.view.MenuItem
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.networking.Article
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.deprecated.CommandOpenNewsDetails
import com.voltek.materialnewsfeed.deprecated.CommandOpenNewsSources
import org.parceler.Parcels
import timber.log.Timber
import javax.inject.Inject

class ListPresenter : ListContract.Presenter() {

    init {
        NewsApp.MainComponent.inject(this)
    }

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mRepository: DataProvider.Articles

    override fun attach(view: ListContract.View, savedInstanceState: Bundle?) {
        super.attach(view, savedInstanceState)

        val swipeToRefresh = mView?.onSwipeToRefresh()
                ?.subscribe({ getArticles() }, { Timber.e(it) })

        val toolbarClicks = mView?.toolbarClicks()
                ?.subscribe({ onOptionsItemSelected(it) }, { Timber.e(it) })

        val listClicks = mView?.onItemClick()
                ?.subscribe({ mRouter.execute(CommandOpenNewsDetails(it)) }, { Timber.e(it) })

        mDisposable.addAll(swipeToRefresh, toolbarClicks, listClicks)
    }

    override fun onFirstLaunch() {
        getArticles()
    }

    override fun onRestore(savedInstanceState: Bundle) {
        val articles: List<Article> = Parcels.unwrap(savedInstanceState.getParcelable(ListFragment.BUNDLE_ARTICLES))
        if (articles.isEmpty()) {
            getArticles()
        } else {
            mView?.handleResponse(articles)
        }
    }

    fun getArticles() {
        mRepository.getAll()
                .filter { !it.isEmpty() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mView?.handleLoading() }
                .subscribe({
                    mView?.handleResponse(it)
                }, {
                    mView?.handleError(it.toString())
                })
    }

    fun onOptionsItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.action_news_sources -> mRouter.execute(CommandOpenNewsSources())
        }
    }
}
