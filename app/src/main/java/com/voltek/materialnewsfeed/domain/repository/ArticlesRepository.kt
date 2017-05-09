package com.voltek.materialnewsfeed.domain.repository

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.Provider
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.data.exception.NoConnectionException
import com.voltek.materialnewsfeed.domain.interactor.Result
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesRepository {

    @Inject
    lateinit var mNet: Provider.Api.Articles

    @Inject
    lateinit var mRes: Provider.Platform.Resources

    init {
        NewsApp.repositoryComponent.inject(this)
    }

    fun get(sources: List<Source>): Observable<Result<List<Article>?>> = Observable.create {
        val emitter = it

        if (!sources.isEmpty()) {
            for (source in sources) {
                mNet.get(source.id)
                        .subscribe({
                            val result = ArrayList<Article>()
                            val sourceTitle = Article()
                            sourceTitle.source = source.name
                            result.add(sourceTitle)
                            result.addAll(it)
                            emitter.onNext(Result(result))
                        }, {
                            val message: String = when (it) {
                                is NoConnectionException -> mRes.getString(R.string.error_no_connection)
                                else -> mRes.getString(R.string.error_retrieve_failed, source.name)
                            }
                            emitter.onNext(Result(null, message))
                        })
            }
        } else {
            emitter.onError(Exception(mRes.getString(R.string.error_no_news_sources_selected)))
        }

        emitter.onComplete()
    }
}
