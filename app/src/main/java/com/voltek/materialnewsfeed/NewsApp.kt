package com.voltek.materialnewsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voltek.materialnewsfeed.di.component.DaggerDataComponent
import com.voltek.materialnewsfeed.di.component.DataComponent
import com.voltek.materialnewsfeed.di.module.AppModule
import com.voltek.materialnewsfeed.di.module.NetworkModule
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

        // DI
        lateinit var dataComponent: DataComponent
    }

    override fun onCreate() {
        super.onCreate()

        val appModule = AppModule(this)
        val networkModule = NetworkModule()

        dataComponent = DaggerDataComponent.builder()
                .appModule(appModule)
                .networkModule(networkModule)
                .build()

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
