package com.voltek.materialnewsfeed.ui.news_sources

import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.BaseActivity

class NewsSourcesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        setupToolbar()

        if (supportFragmentManager.findFragmentByTag(NewsSourcesFragment.TAG) == null) {
            replaceFragment(
                    NewsSourcesFragment.newInstance(),
                    R.id.fragment_container,
                    NewsSourcesFragment.TAG)
        }
    }
}
