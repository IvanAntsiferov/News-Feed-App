package com.voltek.materialnewsfeed.presentation.details

import com.voltek.materialnewsfeed.ui.BaseActivity

class DetailsActivity : com.voltek.materialnewsfeed.ui.BaseActivity() {

    companion object {
        const val TAG = "DetailsActivity"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.voltek.materialnewsfeed.R.layout.activity_generic)
        setupToolbar(displayHomeAsUpEnabled = true)

        val article: com.voltek.materialnewsfeed.data.entity.Article = org.parceler.Parcels.unwrap(intent.getParcelableExtra(com.voltek.materialnewsfeed.ui.details.DetailsFragment.Companion.ARG_ARTICLE))

        if (savedInstanceState == null)
            replaceFragment(
                    com.voltek.materialnewsfeed.ui.details.DetailsFragment.Companion.newInstance(article),
                    com.voltek.materialnewsfeed.R.id.fragment_container,
                    com.voltek.materialnewsfeed.ui.details.DetailsFragment.Companion.TAG)
    }

    override fun executeCommand(command: com.voltek.materialnewsfeed.navigation.proxy.Command): Boolean = when (command) {
        is CommandShareArticle -> shareArticle(command)
        is com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite -> openWebsite(command)
        else -> false
    }
}
