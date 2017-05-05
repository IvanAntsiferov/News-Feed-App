package com.voltek.materialnewsfeed.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.navigation.command.CommandStartActivity
import com.voltek.materialnewsfeed.navigation.proxy.Command
import com.voltek.materialnewsfeed.ui.BaseActivity
import com.voltek.materialnewsfeed.ui.details.CommandShareArticle
import com.voltek.materialnewsfeed.ui.details.DetailsActivity
import com.voltek.materialnewsfeed.ui.details.DetailsFragment
import kotlinx.android.synthetic.main.activity_list.*
import org.parceler.Parcels

class ListActivity : BaseActivity() {

    companion object {
        const val TAG = "ListActivity"
    }

    private var mDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupToolbar()
        title = getString(R.string.title_list)

        mDualPane = details_fragment_container != null

        if (savedInstanceState == null)
            replaceFragment(
                    ListFragment.newInstance(),
                    R.id.list_fragment_container,
                    ListFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_list, menu)
        return true
    }

    override fun executeCommand(command: Command): Boolean = when (command) {
        is CommandStartActivity -> startActivity(command)
        is CommandOpenArticleDetails -> openDetails(command.article)
        is CommandShareArticle -> shareArticle(command)
        else -> false
    }

    private fun openDetails(article: Article): Boolean {
        if (mDualPane) {
            replaceFragment(
                    DetailsFragment.newInstance(article),
                    R.id.details_fragment_container,
                    DetailsFragment.TAG)
        } else {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsFragment.ARG_ARTICLE, Parcels.wrap(article))
            startActivity(intent)
        }
        return true
    }
}
