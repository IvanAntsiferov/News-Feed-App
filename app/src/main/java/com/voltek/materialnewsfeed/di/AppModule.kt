package com.voltek.materialnewsfeed.di

import android.content.Context
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: MaterialNewsFeedApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): MaterialNewsFeedApp = app
}
