package com.voltek.materialnewsfeed.utils

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.voltek.materialnewsfeed.R

object ActivityUtils {

    fun setupToolbar(activity: AppCompatActivity) {
        val toolbar = activity.findViewById(R.id.toolbar) as Toolbar
        activity.setSupportActionBar(toolbar)
    }
}
