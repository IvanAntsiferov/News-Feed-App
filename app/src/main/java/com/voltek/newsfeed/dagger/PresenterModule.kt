package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.domain.usecase.articles.GetArticlesUseCase
import com.voltek.newsfeed.domain.usecase.newssources.EnableNewsSourceUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUpdatesUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.navigation.proxy.Router
import com.voltek.newsfeed.presentation.ui.details.DetailsPresenter
import com.voltek.newsfeed.presentation.ui.list.ListPresenter
import com.voltek.newsfeed.presentation.ui.newssources.NewsSourcesPresenter
import com.voltek.newsfeed.presentation.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideSplashPresenter(router: Router, newsSourcesUseCase: NewsSourcesUseCase)
            = SplashPresenter(router, newsSourcesUseCase)

    @Provides
    fun provideNewsSourcesPresenter(
            newsSourcesUseCase: NewsSourcesUseCase,
            enableNewsSourceUseCase: EnableNewsSourceUseCase
    ) = NewsSourcesPresenter(newsSourcesUseCase, enableNewsSourceUseCase)

    @Provides
    fun provideListPresenter(router: Router,
                             getArticlesUseCase: GetArticlesUseCase,
                             newsSourcesUpdatesUseCase: NewsSourcesUpdatesUseCase
    ) = ListPresenter(router, getArticlesUseCase, newsSourcesUpdatesUseCase)

    @Provides
    fun provideDetailsPresenter(router: Router) = DetailsPresenter(router)
}
