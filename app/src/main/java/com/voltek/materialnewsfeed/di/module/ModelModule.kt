package com.voltek.materialnewsfeed.di.module

import com.voltek.materialnewsfeed.ui.BaseInteractor
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesInteractor
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideNewsSourcesInteractor(): NewsSourcesInteractor {
        return NewsSourcesInteractor()
    }

    @Provides
    fun provideNewsSourcesInteractorInterface(interactor: NewsSourcesInteractor): BaseInteractor<NewsSourcesModel> {
        return interactor
    }
}
