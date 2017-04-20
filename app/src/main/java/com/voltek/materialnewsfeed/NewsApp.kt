package com.voltek.materialnewsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voltek.materialnewsfeed.di.AppModule
import com.voltek.materialnewsfeed.di.DaggerMainComponent
import com.voltek.materialnewsfeed.di.MainComponent
import com.voltek.mvpdemo.library.navigation.RouterHolder
import com.voltek.mvpdemo.library.navigation.proxy.RouterBinder
import com.voltek.mvpdemo.library.navigation.proxy.RouterBus
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

        fun getRouterBus(): RouterBus = routerHolder

        fun getRouterBinder(): RouterBinder = routerHolder

        // DI
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
