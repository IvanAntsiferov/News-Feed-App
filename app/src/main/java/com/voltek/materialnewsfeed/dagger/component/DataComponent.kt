package com.voltek.materialnewsfeed.dagger.component

import com.voltek.materialnewsfeed.dagger.module.AppModule
import com.voltek.materialnewsfeed.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface DataComponent {

    //
}
