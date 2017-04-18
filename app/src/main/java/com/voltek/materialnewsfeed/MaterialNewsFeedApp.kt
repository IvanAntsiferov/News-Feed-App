package com.voltek.materialnewsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voltek.materialnewsfeed.di.AppModule
import com.voltek.materialnewsfeed.di.DaggerMainComponent
import com.voltek.materialnewsfeed.di.MainComponent
import io.realm.Realm
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MaterialNewsFeedApp : Application() {

    companion object {
        const val BASE_URL = "https://newsapi.org/"

        private const val FONT_PATH = "fonts/Roboto-Regular.ttf"

        lateinit var MainComponent: MainComponent
    }

    override fun onCreate() {
        super.onCreate()

        MainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(this))
                .build()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath(FONT_PATH)
                .setFontAttrId(R.attr.fontPath)
                .build())

        Realm.init(this)
        Hawk.init(this).build()
        Timber.plant(Timber.DebugTree())
    }
}
