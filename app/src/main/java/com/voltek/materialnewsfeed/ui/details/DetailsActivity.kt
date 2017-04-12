package com.voltek.materialnewsfeed.ui.details

import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.BaseActivity
import com.voltek.materialnewsfeed.utils.ActivityUtils
import org.parceler.Parcels

class DetailsActivity : BaseActivity(),
    DetailsContract.Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        ActivityUtils.setupToolbar(this)

        val article: Article = Parcels.unwrap(intent.getParcelableExtra(DetailsFragment.ARG_ARTICLE))

        if (supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,
                            DetailsFragment.newInstance(article),
                            DetailsFragment.TAG)
                    .commit()
        }
    }
}
