package com.voltek.materialnewsfeed.data.network

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.Provider

class ApiDelegate : Provider.Api.Articles, Provider.Api.NewsSources {

    init {
        NewsApp.dataComponent.inject(this)
    }
}
