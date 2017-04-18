package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.parceler.Parcels
import timber.log.Timber
import javax.inject.Inject

class NewsSourcesPresenter(navigator: NewsSourcesContract.Navigator) : NewsSourcesContract.Presenter(navigator) {

    init {
        MaterialNewsFeedApp.MainComponent.inject(this)
    }

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mRepository: DataProvider.NewsSources

    override fun attach(view: NewsSourcesContract.View, savedInstanceState: Bundle?) {
        super.attach(view, savedInstanceState)

        val toolbarClicks = mNavigator.toolbarClicks()
                .subscribe(this::onOptionsItemSelected, Timber::e)

        mDisposable.addAll(toolbarClicks)
    }

    override fun onFirstLaunch() {
        getSources()
    }

    override fun onRestore(savedInstanceState: Bundle?) {
        val sources: List<Source> = Parcels.unwrap(
                savedInstanceState?.getParcelable(NewsSourcesFragment.BUNDLE_SOURCES))
        if (sources.isEmpty()) {
            getSources()
        } else {
            mView?.handleResponse(sources)
        }
    }

    fun getSources() {
        mView?.handleLoading()

        mRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView?.handleResponse(it)
                }, {
                    mView?.handleError(it.toString())
                })
    }

    fun onOptionsItemSelected(item: MenuItem) {
        when (item.itemId) {
            //
        }
    }
}
