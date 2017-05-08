package com.voltek.materialnewsfeed.data.db

import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.data.Provider

class DatabaseDelegate : Provider.Database.NewsSources {

    init {
        NewsApp.dataComponent.inject(this)
    }
}
