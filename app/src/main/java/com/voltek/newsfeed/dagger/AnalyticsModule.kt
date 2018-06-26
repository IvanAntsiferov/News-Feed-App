package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.BuildConfig
import com.voltek.newsfeed.analytics.Analytics
import com.voltek.newsfeed.analytics.AnalyticsKit
import com.voltek.newsfeed.analytics.ComposedAnalytics
import com.voltek.newsfeed.analytics.kits.DebugAnalyticsKit
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Provides
    fun provideDebugAnalyticsKit() = DebugAnalyticsKit()

    @Provides
    @Singleton
    fun provideAnalytics(debugAnalyticsKit: DebugAnalyticsKit): Analytics {
        val kits = mutableListOf<AnalyticsKit>()
        if (BuildConfig.DEBUG) kits.add(debugAnalyticsKit)
        return ComposedAnalytics(kits)
    }
}
