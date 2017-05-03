package com.voltek.materialnewsfeed.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulerModule {

    companion object {
        const val JOB = "JOB"
        const val UI = "UI"
    }

    @Provides
    @Singleton
    @Named(JOB)
    fun provideJobScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @Provides
    @Singleton
    @Named(UI)
    fun provideUIScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
