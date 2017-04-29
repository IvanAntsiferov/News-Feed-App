package com.voltek.materialnewsfeed.data.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.parceler.Parcel

@RealmClass
@Parcel
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
