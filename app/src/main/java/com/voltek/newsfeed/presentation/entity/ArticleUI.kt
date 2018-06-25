package com.voltek.newsfeed.presentation.entity

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleUI(
        val title: String? = null,
        val description: String? = null,
        val url: String? = null,
        val urlToImage: String? = null
) : Parcelable {
    fun isEmpty() = (title == null && description == null && url == null && urlToImage == null)

    @IgnoredOnParcel
    var source: String = ""
}
