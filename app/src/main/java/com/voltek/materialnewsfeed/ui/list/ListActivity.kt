package com.voltek.materialnewsfeed.ui.list

import android.os.Bundle
import android.view.Menu
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.navigation.proxy.Command
import com.voltek.materialnewsfeed.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity() {

    private var mDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupToolbar()
        title = getString(R.string.title_list)

        mDualPane = details_fragment_container != null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_list, menu)
        return true
    }

    override fun executeCommand(command: Command): Boolean {
        return false
    }

    private fun openDetails(article: Article) {
        if (mDualPane) {
            //
        } else {
            //
        }
    }
}
