package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.api.Source
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.parceler.Parcels
import javax.inject.Inject

class NewsSourcesPresenter : NewsSourcesContract.Presenter() {

    init {
        MaterialNewsFeedApp.MainComponent.inject(this)
    }

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mRepository: DataProvider.NewsSources

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
}
