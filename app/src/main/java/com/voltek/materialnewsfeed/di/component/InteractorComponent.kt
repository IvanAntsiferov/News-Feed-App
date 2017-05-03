package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.di.module.RepositoryModule
import com.voltek.materialnewsfeed.di.module.SchedulerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        SchedulerModule::class,
        RepositoryModule::class)
)
interface InteractorComponent {

    //fun inject(interactor: )
}
