package com.voltek.materialnewsfeed.ui.list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.details.DetailsActivity
import com.voltek.materialnewsfeed.ui.details.DetailsFragment
import com.voltek.materialnewsfeed.utils.ActivityUtils
import org.parceler.Parcels

class ListActivity : AppCompatActivity(),
    ListContract.Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        ActivityUtils.setupToolbar(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                        ListFragment.newInstance(),
                        ListFragment::class.java.simpleName)
                .commit()
    }

    override fun openDetails(article: Article) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsFragment.ARG_ARTICLE, Parcels.wrap(article))
        startActivity(intent)
    }
}
