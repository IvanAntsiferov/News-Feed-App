package com.voltek.newsfeed.data.platform

import android.content.Context
import android.support.annotation.StringRes

class ResourcesManager(private val context: Context) {

    fun getString(@StringRes id: Int): String = context.getString(id)
}
