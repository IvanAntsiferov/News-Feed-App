package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.di.module.UseCaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        UseCaseModule::class)
)
interface PresenterComponent {

    //
}
