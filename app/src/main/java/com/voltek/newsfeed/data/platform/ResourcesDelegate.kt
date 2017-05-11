package com.voltek.newsfeed.data.platform

import android.content.Context
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.data.Provider
import javax.inject.Inject

class ResourcesDelegate : Provider.Platform.Resources {

    @Inject
    lateinit var mContext: Context

    init {
        NewsApp.dataComponent.inject(this)
    }

    override fun getString(id: Int): String = mContext.getString(id)
}
