package com.voltek.materialnewsfeed.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.BaseActivity
import com.voltek.materialnewsfeed.ui.details.DetailsActivity
import com.voltek.materialnewsfeed.ui.details.DetailsFragment
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesActivity
import org.parceler.Parcels
import kotlinx.android.synthetic.main.activity_list.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.toolbar.*

class ListActivity : BaseActivity(),
        ListContract.Navigator {

    private var mDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupToolbar()
        title = getString(R.string.title_list)

        mDualPane = details_fragment_container != null

        if (supportFragmentManager.findFragmentByTag(ListFragment.TAG) == null) {
            replaceFragment(
                    ListFragment.newInstance(),
                    R.id.list_fragment_container,
                    ListFragment.TAG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_list, menu)
        return true
    }

    override fun toolbarClicks(): Observable<MenuItem> = RxToolbar.itemClicks(toolbar)

    override fun isDualPane(): Boolean = mDualPane

    override fun openDetails(article: Article) {
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
    }

    override fun openNewsSources() {
        val intent = Intent(this, NewsSourcesActivity::class.java)
        startActivity(intent)
    }
}
