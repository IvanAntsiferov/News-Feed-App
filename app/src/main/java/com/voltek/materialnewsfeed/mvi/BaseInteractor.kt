package com.voltek.materialnewsfeed.mvi

import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface BaseInteractor<Model : BaseModel, Event : BaseEvent> {

    /**
     * Emits view state
     */
    fun stateChangesFeed(): Observable<Model>

    /**
     * Emits input events from view
     */
    fun inputEventsFeed(): Subject<Event>
}
