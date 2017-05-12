package com.voltek.newsfeed.presentation.details

import android.os.Bundle
import com.voltek.newsfeed.R
import com.voltek.newsfeed.data.entity.Article
import com.voltek.newsfeed.navigation.command.CommandOpenWebsite
import com.voltek.newsfeed.navigation.proxy.Command
import com.voltek.newsfeed.presentation.BaseActivity
import org.parceler.Parcels

class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        setupToolbar(displayHomeAsUpEnabled = true)

        val article: Article = Parcels.unwrap(intent.getParcelableExtra(DetailsFragment.ARG_ARTICLE))
        title = article.source

        if (savedInstanceState == null)
            replaceFragment(
                    DetailsFragment.newInstance(article),
                    R.id.fragment_container,
                    DetailsFragment.TAG
            )
    }

    override fun executeCommand(command: Command): Boolean = when (command) {
        is CommandShareArticle -> shareArticle(command)
        is CommandOpenWebsite -> openWebsite(command)
        else -> false
    }
}
