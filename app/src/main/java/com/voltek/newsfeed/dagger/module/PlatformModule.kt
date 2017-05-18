package com.voltek.newsfeed.dagger.module

import com.voltek.newsfeed.data.Provider
import com.voltek.newsfeed.data.platform.ResourcesManager
import dagger.Module
import dagger.Provides

@Module
class PlatformModule {

    @Provides
    fun provideResourcesManager(): Provider.Platform.Resources = ResourcesManager()
}
