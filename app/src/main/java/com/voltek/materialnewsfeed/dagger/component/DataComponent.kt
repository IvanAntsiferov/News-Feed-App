package com.voltek.materialnewsfeed.dagger.component

import com.voltek.materialnewsfeed.dagger.module.AppModule
import com.voltek.materialnewsfeed.dagger.module.NetworkModule
import com.voltek.materialnewsfeed.data.db.DatabaseDelegate
import com.voltek.materialnewsfeed.data.network.ApiDelegate
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface DataComponent {

    fun inject(database: DatabaseDelegate)

    fun inject(api: ApiDelegate)
}
