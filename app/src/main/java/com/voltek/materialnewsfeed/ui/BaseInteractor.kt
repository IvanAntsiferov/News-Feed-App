package com.voltek.materialnewsfeed.ui

import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface BaseInteractor<Model : BaseModel, Event : BaseEvent> {

    fun stateChangesFeed(): Observable<Model>

    fun inputEventsFeed(): Subject<Event>
}
