package com.voltek.materialnewsfeed.ui.details

sealed class DetailsModel {

    class Loaded(
            val author: String,
            val title: String,
            val description: String,
            val urlToImage: String,
            val publishedAt: String
    ) : DetailsModel()

    class Error : DetailsModel()
}
