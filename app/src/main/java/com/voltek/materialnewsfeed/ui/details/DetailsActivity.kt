package com.voltek.materialnewsfeed.ui.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.api.Article
import com.voltek.materialnewsfeed.utils.ActivityUtils
import org.parceler.Parcels

class DetailsActivity : AppCompatActivity(),
    DetailsContract.Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        ActivityUtils.setupToolbar(this)

        val article: Article = Parcels.unwrap(intent.getParcelableExtra(DetailsFragment.ARG_ARTICLE))
    }
}
