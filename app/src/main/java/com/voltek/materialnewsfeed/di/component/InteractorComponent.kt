package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        RepositoryModule::class)
)
interface InteractorComponent {

    //
}
