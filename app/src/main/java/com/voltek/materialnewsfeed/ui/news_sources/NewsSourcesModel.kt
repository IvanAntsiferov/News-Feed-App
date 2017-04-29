package com.voltek.materialnewsfeed.ui.news_sources

import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.ui.BaseModel

sealed class NewsSourcesModel : BaseModel {

    class Loading : NewsSourcesModel()

    class Result(items: List<Source>) : NewsSourcesModel()

    class Error(message: String) : NewsSourcesModel()
}
