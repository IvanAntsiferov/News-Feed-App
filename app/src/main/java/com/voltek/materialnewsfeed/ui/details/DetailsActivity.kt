package com.voltek.materialnewsfeed.ui.details

import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.BaseActivity
import org.parceler.Parcels

class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        setupToolbar()

        val article: Article = Parcels.unwrap(intent.getParcelableExtra(DetailsFragment.ARG_ARTICLE))

        if (supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) == null) {
            replaceFragment(
                    DetailsFragment.newInstance(article),
                    R.id.fragment_container,
                    DetailsFragment.TAG)
        }
    }
}
