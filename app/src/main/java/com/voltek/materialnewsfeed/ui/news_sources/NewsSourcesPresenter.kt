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

        mRepository.provideNewsSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView?.handleResponse(it)
                }, {
                    mView?.handleError(it.toString())
                })
    }
}
