package com.voltek.materialnewsfeed.ui.news_sources

import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.deprecated.BaseActivity
import com.voltek.materialnewsfeed.navigation.proxy.Command

class NewsSourcesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        setupToolbar(displayHomeAsUpEnabled = true)

        if (supportFragmentManager.findFragmentByTag(NewsSourcesFragment.TAG) == null) {
            replaceFragment(
                    NewsSourcesFragment.newInstance(),
                    R.id.fragment_container,
                    NewsSourcesFragment.TAG)
        }
    }

    override fun executeCommand(command: Command): Boolean {
        return false
    }
}
