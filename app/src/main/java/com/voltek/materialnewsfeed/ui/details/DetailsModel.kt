package com.voltek.materialnewsfeed.ui.details

import com.voltek.materialnewsfeed.ui.BaseModel

sealed class DetailsModel : BaseModel {

    class Loaded(
            val author: String,
            val title: String,
            val description: String,
            val urlToImage: String,
            val publishedAt: String
    ) : DetailsModel()

    class Error : DetailsModel()
}
