package com.voltek.materialnewsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voltek.materialnewsfeed.di.AppModule
import com.voltek.materialnewsfeed.di.DaggerMainComponent
import com.voltek.materialnewsfeed.di.MainComponent
import timber.log.Timber

class MaterialNewsFeedApp : Application() {

    companion object {
        const val BASE_URL = "https://newsapi.org/"

        lateinit var mainComponent: MainComponent
    }

    override fun onCreate() {
        super.onCreate()

        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(this))
                .build()

        Hawk.init(this).build()

        Timber.plant()
    }
}
