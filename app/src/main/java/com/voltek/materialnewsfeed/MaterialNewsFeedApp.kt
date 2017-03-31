package com.voltek.materialnewsfeed

import android.app.Application

class MaterialNewsFeedApp : Application() {

    companion object {
        val BASE_URL = "https://newsapi.org/"
    }

    override fun onCreate() {
        super.onCreate()
    }
}
