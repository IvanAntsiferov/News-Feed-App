package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.NewsSourcesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class NewsSourcesPresenter : NewsSourcesContract.Presenter() {

    init {
        MaterialNewsFeedApp.mainComponent.inject(this)
    }

    @Inject
    lateinit var mContext: Context

    private var mRepository: DataProvider.NewsSources = NewsSourcesRepository()

    override fun onFirstLaunch() {
        getSources()
    }

    override fun onRestore(savedInstanceState: Bundle?) {
        //
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
