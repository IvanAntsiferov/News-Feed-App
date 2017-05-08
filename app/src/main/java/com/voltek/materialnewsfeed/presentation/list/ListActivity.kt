package com.voltek.materialnewsfeed.presentation.list

import com.voltek.materialnewsfeed.ui.BaseActivity
import com.voltek.materialnewsfeed.ui.details.CommandShareArticle
import com.voltek.materialnewsfeed.ui.details.DetailsActivity
import com.voltek.materialnewsfeed.ui.details.DetailsFragment

class ListActivity : com.voltek.materialnewsfeed.ui.BaseActivity() {

    companion object {
        const val TAG = "ListActivity"
    }

    private var mDualPane = false

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.voltek.materialnewsfeed.R.layout.activity_list)
        setupToolbar()
        title = getString(com.voltek.materialnewsfeed.R.string.title_list)

        mDualPane = kotlinx.android.synthetic.main.activity_list.details_fragment_container != null

        if (savedInstanceState == null)
            replaceFragment(
                    com.voltek.materialnewsfeed.ui.list.ListFragment.Companion.newInstance(),
                    com.voltek.materialnewsfeed.R.id.list_fragment_container,
                    com.voltek.materialnewsfeed.ui.list.ListFragment.Companion.TAG)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(com.voltek.materialnewsfeed.R.menu.menu_activity_list, menu)
        return true
    }

    override fun executeCommand(command: com.voltek.materialnewsfeed.navigation.proxy.Command): Boolean = when (command) {
        is com.voltek.materialnewsfeed.navigation.command.CommandStartActivity -> startActivity(command)
        is com.voltek.materialnewsfeed.ui.list.CommandOpenArticleDetails -> openDetails(command.article)
        is com.voltek.materialnewsfeed.ui.details.CommandShareArticle -> shareArticle(command)
        is com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite -> openWebsite(command)
        else -> false
    }

    private fun openDetails(article: com.voltek.materialnewsfeed.data.entity.Article): Boolean {
        if (mDualPane) {
            replaceFragment(
                    com.voltek.materialnewsfeed.ui.details.DetailsFragment.Companion.newInstance(article),
                    com.voltek.materialnewsfeed.R.id.details_fragment_container,
                    com.voltek.materialnewsfeed.ui.details.DetailsFragment.Companion.TAG)
        } else {
            val intent = android.content.Intent(this, DetailsActivity::class.java)
            intent.putExtra(com.voltek.materialnewsfeed.ui.details.DetailsFragment.Companion.ARG_ARTICLE, org.parceler.Parcels.wrap(article))
            startActivity(intent)
        }
        return true
    }
}
