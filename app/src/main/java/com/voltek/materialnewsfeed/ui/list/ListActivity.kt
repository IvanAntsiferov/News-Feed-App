package com.voltek.materialnewsfeed.ui.list

import android.content.Intent
import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.BaseActivity
import com.voltek.materialnewsfeed.ui.details.DetailsActivity
import com.voltek.materialnewsfeed.ui.details.DetailsFragment
import org.parceler.Parcels
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity(),
        ListContract.Navigator {

    private var mDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupToolbar()

        mDualPane = details_fragment_container != null

        if (supportFragmentManager.findFragmentByTag(ListFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.list_fragment_container,
                            ListFragment.newInstance(),
                            ListFragment.TAG)
                    .commit()
        }
    }

    override fun isDualPane(): Boolean = mDualPane

    override fun openDetails(article: Article) {
        if (mDualPane) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment_container,
                            DetailsFragment.newInstance(article),
                            DetailsFragment.TAG)
                    .commit()
        } else {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsFragment.ARG_ARTICLE, Parcels.wrap(article))
            startActivity(intent)
        }
    }
}
