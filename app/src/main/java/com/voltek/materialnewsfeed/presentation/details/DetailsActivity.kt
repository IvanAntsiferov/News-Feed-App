package com.voltek.materialnewsfeed.presentation.details

import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.presentation.BaseActivity
import org.parceler.Parcels

class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.voltek.materialnewsfeed.R.layout.activity_generic)
        setupToolbar(displayHomeAsUpEnabled = true)

        val article: Article = Parcels.unwrap(intent.getParcelableExtra(DetailsFragment.Companion.ARG_ARTICLE))

        if (savedInstanceState == null)
            replaceFragment(
                    DetailsFragment.Companion.newInstance(article),
                    R.id.fragment_container,
                    DetailsFragment.Companion.TAG)
    }

    override fun executeCommand(command: com.voltek.materialnewsfeed.navigation.proxy.Command): Boolean = when (command) {
        is CommandShareArticle -> shareArticle(command)
        is com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite -> openWebsite(command)
        else -> false
    }
}
