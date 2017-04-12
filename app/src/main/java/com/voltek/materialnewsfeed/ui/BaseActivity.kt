package com.voltek.materialnewsfeed.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.voltek.materialnewsfeed.utils.ActivityUtils

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.setupToolbar(this)
    }
}
