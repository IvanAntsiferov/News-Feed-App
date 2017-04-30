package com.voltek.materialnewsfeed

import android.app.Application
import android.content.Context
import com.orhanobut.hawk.Hawk
import com.voltek.materialnewsfeed.navigation.RouterHolder
import com.voltek.materialnewsfeed.navigation.proxy.NavigatorBinder
import com.voltek.materialnewsfeed.navigation.proxy.Router
import io.realm.Realm
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class NewsApp : Application() {

    companion object {

        // Const
        const val BASE_URL = "https://newsapi.org/"

        private const val FONT_PATH = "fonts/Roboto-Regular.ttf"

        // Navigation
        private val routerHolder: RouterHolder = RouterHolder()

        fun getRouter(): Router = routerHolder

        fun getNavigatorBinder(): NavigatorBinder = routerHolder

        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        // Libs init
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath(FONT_PATH)
                .setFontAttrId(R.attr.fontPath)
                .build())
        Realm.init(this)
        Hawk.init(this).build()
        Timber.plant(Timber.DebugTree())
    }
}
