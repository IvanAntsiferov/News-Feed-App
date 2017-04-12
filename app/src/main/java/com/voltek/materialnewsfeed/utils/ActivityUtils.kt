package com.voltek.materialnewsfeed.utils

import android.support.v7.widget.Toolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.BaseActivity

object ActivityUtils {

    fun setupToolbar(activity: BaseActivity) {
        val toolbar = activity.findViewById(R.id.toolbar) as Toolbar
        activity.setSupportActionBar(toolbar)
    }
}
