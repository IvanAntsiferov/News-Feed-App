package com.voltek.materialnewsfeed.presentation.news_sources

import com.voltek.materialnewsfeed.presentation.BaseActivity

class NewsSourcesActivity : BaseActivity() {

    companion object {
        const val TAG = "NewsSourcesActivity"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.voltek.materialnewsfeed.R.layout.activity_generic)
        setupToolbar(displayHomeAsUpEnabled = true)

        if (savedInstanceState == null)
            replaceFragment(
                    com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesFragment.Companion.newInstance(),
                    com.voltek.materialnewsfeed.R.id.fragment_container,
                    com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesFragment.Companion.TAG)
    }

    override fun executeCommand(command: com.voltek.materialnewsfeed.navigation.proxy.Command): Boolean {
        return false
    }
}
