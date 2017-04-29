package com.voltek.materialnewsfeed.ui

import io.reactivex.Observable

interface BaseInteractor<Model : BaseModel> {

    fun StateChangesFeed(): Observable<Model>
}
