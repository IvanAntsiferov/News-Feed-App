package com.voltek.newsfeed.navigation.command

import com.voltek.newsfeed.navigation.proxy.Command

class CommandSystemMessage(
        val message: String = "",
        val error: Exception? = null
) : Command()
