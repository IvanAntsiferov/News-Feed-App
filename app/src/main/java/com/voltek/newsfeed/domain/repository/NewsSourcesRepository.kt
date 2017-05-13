package com.voltek.newsfeed.domain.repository

import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.R
import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.entity.Source
import com.voltek.newsfeed.data.exception.NoConnectionException
import com.voltek.newsfeed.domain.interactor.Result
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class NewsSourcesRepository {

    @Inject
    lateinit var mNet: Provider.Api.NewsSources

    @Inject
    lateinit var mDb: Provider.Database.NewsSources

    @Inject
    lateinit var mRes: Provider.Platform.Resources

    private var mSourcesEnabledSubject: PublishSubject<Unit> = PublishSubject.create()

    fun getSourcesEnabledObservable(): Observable<Unit> = mSourcesEnabledSubject

    init {
       NewsApp.repositoryComponent.inject(this)
    }

    fun getAll(): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        val sourcesCache = mDb.queryAll()

        if (sourcesCache.isEmpty()) {
            mNet.get()
                    .subscribe({
                        mDb.save(it)
                        emitter.onNext(Result(mDb.queryAll()))
                    }, {
                        val message: String = when (it) {
                            is NoConnectionException -> mRes.getString(R.string.error_no_connection)
                            else -> mRes.getString(R.string.error_request_failed)
                        }
                        emitter.onNext(Result(null, message))
                    })
        } else {
            emitter.onNext(Result(sourcesCache))
        }

        emitter.onComplete()
    }

    fun getCategory(category: String): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        val query: List<Source>
        var message = ""

        if (category == mRes.getString(R.string.category_all)) {
            query = mDb.queryAll()

            if (query.isEmpty())
                message = mRes.getString(R.string.error_no_news_sources_loaded)
        } else if (category == mRes.getString(R.string.category_enabled) || category.isEmpty()) {
            query = mDb.queryEnabled()

            if (query.isEmpty())
                message = mRes.getString(R.string.error_no_news_sources_selected_yet)
        } else {
            query = mDb.queryCategory(category.toLowerCase())

            if (query.isEmpty())
                message = mRes.getString(R.string.error_no_news_sources_for_category)
        }

        emitter.onNext(Result(query, message))
        emitter.onComplete()
    }

    fun refresh(): Observable<Result<List<Source>?>> = Observable.create {
        val emitter = it

        mNet.get()
                .subscribe({
                    val current = mDb.queryEnabled()
                    val new = it as ArrayList

                    for (source in new)
                        for (enabled in current)
                            if (source.id == enabled.id)
                                source.isEnabled = true

                    mDb.deleteAll()
                    mDb.save(new)
                    emitter.onNext(Result(mDb.queryAll()))
                }, {
                    val message: String = when (it) {
                        is NoConnectionException -> mRes.getString(R.string.error_no_connection)
                        else -> mRes.getString(R.string.error_request_failed)
                    }
                    emitter.onNext(Result(mDb.queryAll(), message))
                })

        emitter.onComplete()
    }

    fun update(vararg source: Source): Observable<Unit> = Observable.create {
        val emitter = it

        mDb.save(source.toMutableList())

        mSourcesEnabledSubject.onNext(Unit)
        emitter.onComplete()
    }
}
