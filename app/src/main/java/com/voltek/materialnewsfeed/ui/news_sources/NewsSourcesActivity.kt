package com.voltek.materialnewsfeed.ui.news_sources

import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.navigation.proxy.Command
import com.voltek.materialnewsfeed.ui.BaseActivity

class NewsSourcesActivity : BaseActivity() {

    companion object {
        const val TAG = "NewsSourcesActivity"
    }

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
