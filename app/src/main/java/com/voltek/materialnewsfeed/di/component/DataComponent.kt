package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.di.module.AppModule
import com.voltek.materialnewsfeed.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface DataComponent {

    //fun inject()
}
