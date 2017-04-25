package com.voltek.materialnewsfeed.di.module

import android.content.Context
import com.voltek.materialnewsfeed.NewsApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: NewsApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): NewsApp = app
}
