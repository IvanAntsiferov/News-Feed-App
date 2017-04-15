package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.NewsSourcesRepository
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.parceler.Parcels
import timber.log.Timber
import javax.inject.Inject

class NewsSourcesPresenter : NewsSourcesContract.Presenter() {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mContext: Context

    private val mRepository: DataProvider.NewsSources = NewsSourcesRepository()

    override fun attach(view: NewsSourcesContract.View, savedInstanceState: Bundle?) {
        super.attach(view, savedInstanceState)

        val onItemClick = mView!!.onItemClick()
                .subscribe()

        mDisposable.addAll(onItemClick)
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

        val observable = mRepository.provideNewsSources()
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView?.handleResponse(it)
                }, {
                    Timber.e(it)
                })
    }
}
