package com.voltek.materialnewsfeed.di

import com.voltek.materialnewsfeed.ui.details.DetailsFragment
import com.voltek.materialnewsfeed.ui.list.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class)
)
interface MainComponent {

    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailsFragment)
}
