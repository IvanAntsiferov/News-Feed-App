package com.voltek.materialnewsfeed.presentation.details

import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite
import com.voltek.materialnewsfeed.navigation.proxy.Command
import com.voltek.materialnewsfeed.presentation.BaseActivity
import org.parceler.Parcels

class DetailsActivity : BaseActivity() {

    companion object {
        const val TAG = "DetailsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        setupToolbar(displayHomeAsUpEnabled = true)

        val article: Article = Parcels.unwrap(intent.getParcelableExtra(DetailsFragment.ARG_ARTICLE))

        if (savedInstanceState == null)
            replaceFragment(
                    DetailsFragment.newInstance(article),
                    R.id.fragment_container,
                    DetailsFragment.TAG)
    }

    override fun executeCommand(command: Command): Boolean = when (command) {
        is CommandShareArticle -> shareArticle(command)
        is CommandOpenWebsite -> openWebsite(command)
        else -> false
    }
}
