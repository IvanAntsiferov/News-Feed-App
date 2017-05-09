package com.voltek.materialnewsfeed.presentation.news_sources

import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.navigation.proxy.Command
import com.voltek.materialnewsfeed.presentation.BaseActivity

class NewsSourcesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        setupToolbar(displayHomeAsUpEnabled = true)

        if (savedInstanceState == null)
            replaceFragment(
                    NewsSourcesFragment.newInstance(),
                    R.id.fragment_container,
                    NewsSourcesFragment.TAG)
    }

    override fun executeCommand(command: Command): Boolean {
        return false
    }
}
