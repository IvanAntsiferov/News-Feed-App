package com.voltek.materialnewsfeed.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.utils.ActivityUtils

class ListActivity : AppCompatActivity(),
    ListContract.Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        ActivityUtils.setupToolbar(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_list,
                        ListFragment.newInstance(),
                        ListFragment::class.java.simpleName)
                .commit()
    }
}
