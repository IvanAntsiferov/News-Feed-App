package com.voltek.materialnewsfeed.navigation.command

import com.voltek.materialnewsfeed.data.networking.Article
import com.voltek.materialnewsfeed.navigation.proxy.NavigatorCommand

class CommandOpenNewsDetails(val article: Article) : NavigatorCommand(id) {

    companion object {
        const val id = 10;
    }
}
