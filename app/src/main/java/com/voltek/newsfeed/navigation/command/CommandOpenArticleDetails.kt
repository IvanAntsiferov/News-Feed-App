package com.voltek.newsfeed.navigation.command

import com.voltek.newsfeed.data.entity.ArticleRAW
import com.voltek.newsfeed.navigation.proxy.Command

class CommandOpenArticleDetails(val article: ArticleRAW) : Command()
