package com.voltek.newsfeed.domain.interactor

/**
 * Common interactor input parameters class.
 */
data class Parameter<out Type : Any?>(
        val flag: String = "",
        val item: Type? = null
)
