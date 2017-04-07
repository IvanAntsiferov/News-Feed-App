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

    private var mDualPane = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        ActivityUtils.setupToolbar(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.list_fragment_container,
                        ListFragment.newInstance(),
                        ListFragment::class.java.simpleName)
                .commit()
    }

    override fun isDualPane(): Boolean = mDualPane

    override fun openDetails(article: Article) {
        if (mDualPane) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment_container,
                            DetailsFragment.newInstance(article),
                            DetailsFragment::class.java.simpleName)
                    .commit()
        } else{
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsFragment.ARG_ARTICLE, Parcels.wrap(article))
            startActivity(intent)
        }
    }
}
