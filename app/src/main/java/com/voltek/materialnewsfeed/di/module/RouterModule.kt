package com.voltek.materialnewsfeed.di.module

import com.voltek.materialnewsfeed.navigation.RouterHolder
import com.voltek.materialnewsfeed.navigation.proxy.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RouterModule(val routerHolder: RouterHolder) {

    @Provides
    @Singleton
    fun provideRouter(): Router = routerHolder
}
