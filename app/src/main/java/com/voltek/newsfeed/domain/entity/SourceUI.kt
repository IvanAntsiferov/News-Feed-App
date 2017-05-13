package com.voltek.newsfeed.domain.entity

data class SourceUI(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val country: String,
        var isEnabled: Boolean
)
