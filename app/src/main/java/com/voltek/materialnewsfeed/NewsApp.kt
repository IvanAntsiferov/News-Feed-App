package com.voltek.materialnewsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voltek.materialnewsfeed.deprecated.DaggerMainComponent
import com.voltek.materialnewsfeed.deprecated.MainComponent
import com.voltek.materialnewsfeed.di.module.AppModule
import com.voltek.materialnewsfeed.di.component.*
import com.voltek.materialnewsfeed.di.module.*
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
        lateinit var presenterComponent: PresenterComponent

        lateinit var interactorComponent: InteractorComponent

        lateinit var dataComponent: DataComponent

        lateinit var MainComponent: MainComponent
    }

    override fun onCreate() {
        super.onCreate()

        val appModule = AppModule(this)
        val useCaseModule = UseCaseModule()
        val repositoryModule = RepositoryModule()
        val networkModule = NetworkModule()

        // Dependency injection
        presenterComponent = DaggerPresenterComponent.builder()
                .useCaseModule(useCaseModule)
                .build()

        interactorComponent = DaggerInteractorComponent.builder()
                .repositoryModule(repositoryModule)
                .build()

        dataComponent = DaggerDataComponent.builder()
                .appModule(appModule)
                .networkModule(networkModule)
                .build()

        MainComponent = DaggerMainComponent.builder()
                .appModule(appModule)
                .networkModule(networkModule)
                .repositoryModule(repositoryModule)
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
