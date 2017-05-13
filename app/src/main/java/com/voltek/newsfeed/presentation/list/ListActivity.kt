package com.voltek.newsfeed.presentation.list

import com.voltek.newsfeed.R
import com.voltek.newsfeed.data.entity.Article
import com.voltek.newsfeed.navigation.command.CommandOpenArticleDetails
import com.voltek.newsfeed.navigation.command.CommandOpenWebsite
import com.voltek.newsfeed.navigation.command.CommandStartActivity
import com.voltek.newsfeed.navigation.proxy.Command
import com.voltek.newsfeed.presentation.BaseActivity
import com.voltek.newsfeed.navigation.command.CommandShareArticle
import com.voltek.newsfeed.presentation.details.DetailsActivity
import com.voltek.newsfeed.presentation.details.DetailsFragment
import kotlinx.android.synthetic.main.activity_list.*
import org.parceler.Parcels

class ListActivity : BaseActivity() {

    private var mDualPane = false

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupToolbar()
        title = getString(com.voltek.newsfeed.R.string.title_list)

        mDualPane = details_fragment_container != null

        if (savedInstanceState == null)
            replaceFragment(
                    ListFragment.newInstance(),
                    R.id.list_fragment_container,
                    ListFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_list, menu)
        return true
    }

    override fun executeCommand(command: Command): Boolean = when (command) {
        is CommandStartActivity -> startActivity(command)
        is CommandOpenArticleDetails -> openDetails(command.article)
        is CommandShareArticle -> shareArticle(command)
        is CommandOpenWebsite -> openWebsite(command)
        else -> false
    }

    private fun openDetails(article: Article): Boolean {
        if (mDualPane) {
            replaceFragment(
                    DetailsFragment.newInstance(article),
                    R.id.details_fragment_container,
                    DetailsFragment.TAG
            )
        } else {
            val intent = android.content.Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsFragment.ARG_ARTICLE, Parcels.wrap(article))
            startActivity(intent)
        }
        return true
    }
}
