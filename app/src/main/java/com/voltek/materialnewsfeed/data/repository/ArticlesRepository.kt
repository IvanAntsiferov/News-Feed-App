package com.voltek.materialnewsfeed.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.Utils.RepositoryUtils
import com.voltek.materialnewsfeed.data.DataProvider
import com.voltek.materialnewsfeed.data.networking.NewsApi
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.data.exception.NoConnectionException
import com.voltek.materialnewsfeed.data.exception.NoNewsSourcesException
import com.voltek.materialnewsfeed.data.exception.RequestFailedException
import io.reactivex.Observable
import javax.inject.Inject

// TODO refactor ArticlesRepository
class ArticlesRepository : DataProvider.Articles {

    @Inject
    lateinit var mApi: NewsApi

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mSourcesRepo: DataProvider.NewsSources

    override fun get(): Observable<List<Article>> = Observable.create {
        RepositoryUtils.checkConnection(mContext)

        val emitter = it

        val sources = mSourcesRepo.getCategory(mContext.getString(R.string.category_enabled))

        if (!sources.isEmpty()) {
            for (source in sources) {
                val call = mApi.fetchArticles(BuildConfig.ApiKey, source.id).execute()
                if (call.isSuccessful) {
                    emitter.onNext(call.body().articles)
                } else {
                    emitter.onError(RequestFailedException())
                }
            }
        } else {
            emitter.onError(NoNewsSourcesException())
        }

        emitter.onComplete()
    }
}
