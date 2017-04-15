package com.voltek.materialnewsfeed.data.api

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

data class NewsApiSourcesResponse(val sources: List<Source>)

@RealmClass
open class Source : RealmObject() {

    @PrimaryKey
    var id: String = ""

    var name: String= ""

    var description: String= ""

    var url: String= ""

    var category: String= ""

    var language: String= ""

    var country: String= ""

    var isEnabled: Boolean = false
}
