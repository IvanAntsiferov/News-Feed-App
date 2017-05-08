package com.voltek.materialnewsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voltek.materialnewsfeed.dagger.component.*
import com.voltek.materialnewsfeed.dagger.module.*
import com.voltek.materialnewsfeed.navigation.RouterHolder
import com.voltek.materialnewsfeed.navigation.proxy.NavigatorBinder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class NewsApp : Application() {

    companion object {

        // Const
        private const val BASE_URL = "https://newsapi.org/"

        private const val FONT_PATH = "fonts/Roboto-Regular.ttf"

        // Navigation
        private val routerHolder: RouterHolder = RouterHolder()

        fun getNavigatorBinder(): NavigatorBinder = routerHolder

        // DI
        lateinit var presenterComponent: PresenterComponent

        lateinit var interactorComponent: InteractorComponent

        lateinit var repositoryComponent: RepositoryComponent
    }

    override fun onCreate() {
        super.onCreate()

        val appModule = AppModule(this)
        val interactorModule =
                InteractorModule(Schedulers.computation(), AndroidSchedulers.mainThread())
        val networkModule = NetworkModule(BASE_URL)
        val repositoryModule = RepositoryModule()
        val routerModule = RouterModule(routerHolder)

        presenterComponent = DaggerPresenterComponent.builder()
                .routerModule(routerModule)
                .interactorModule(interactorModule)
                .build()

        interactorComponent = DaggerInteractorComponent.builder()
                .repositoryModule(repositoryModule)
                .build()

        repositoryComponent = DaggerRepositoryComponent.builder()
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
