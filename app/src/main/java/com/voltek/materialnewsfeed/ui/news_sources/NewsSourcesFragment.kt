package com.voltek.materialnewsfeed.ui.news_sources

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.BaseFragment
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*

class NewsSourcesFragment : BaseFragment(),
        NewsSourcesView {

    companion object {
        const val TAG = "NewsSourcesFragment"

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
                R.id.action_technology
        )

        fun newInstance(): NewsSourcesFragment = NewsSourcesFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: NewsSourcesPresenter

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recycler_view.hasFixedSize()
        recycler_view.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_news_sources, menu)
    }

    override fun attachInputListeners() {
        RxToolbar.itemClicks(activity.toolbar)
                .filter { !swipe_container.isRefreshing }
                .subscribe({
                    when (it.itemId) {
                        in CATEGORY_ITEMS_IDS -> {
                            if (!it.isChecked) {
                                it.isChecked = true
                                mPresenter.notify(Event.Filter(it.title.toString()))
                            }
                        }
                        R.id.action_refresh -> mPresenter.notify(Event.Refresh())
                    }
                })
                .bind()
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: NewsSourcesContract.NewsSourcesModel) {
        //
    }
}
