package com.voltek.materialnewsfeed.ui.news_sources

import android.view.MenuItem
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.materialnewsfeed.R

@InjectViewState
class NewsSourcesPresenter : MvpPresenter<NewsSourcesView>() {

    companion object {
        private val CATEGORY_ITEMS_IDS = arrayOf(
                R.id.action_all,
                R.id.action_enabled,
                R.id.action_business,
                R.id.action_entertainment,
                R.id.action_gaming,
                R.id.action_general,
                R.id.action_music,
                R.id.action_politics,
                R.id.action_science_and_nature,
                R.id.action_sport,
                R.id.action_technology)
    }

    override fun attachView(view: NewsSourcesView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: NewsSourcesView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }


    fun onOptionsItemSelected(item: MenuItem) {
        when (item.itemId) {
            in CATEGORY_ITEMS_IDS -> {
                /*if (!mIsLoading && !item.isChecked) {
                    item.isChecked = true
                    mView?.filter(item.title.toString())
                }*/
            }
        }
    }
}
