package com.voltek.materialnewsfeed.data.platform

import android.content.Context
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.Provider
import javax.inject.Inject

class ResourcesDelegate : Provider.Platform.Resources {

    @Inject
    lateinit var mContext: Context

    init {
        NewsApp.dataComponent.inject(this)
    }

    override fun getString(id: Int): String = mContext.getString(id)

    override fun getString(id: Int, vararg args: Any): String = mContext.getString(id, args)
}
