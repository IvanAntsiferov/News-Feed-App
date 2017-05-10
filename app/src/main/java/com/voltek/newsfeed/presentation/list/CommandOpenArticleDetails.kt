package com.voltek.newsfeed.presentation.list

import com.voltek.newsfeed.data.entity.Article
import com.voltek.newsfeed.navigation.proxy.Command

class CommandOpenArticleDetails(val article: Article) : Command()
