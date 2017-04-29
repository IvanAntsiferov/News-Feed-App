package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.R.id.*
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber

class NewsSourcesFragment : BaseFragment(),
        NewsSourcesView {

    companion object {
        const val TAG = "NewsSourcesFragment"
        const val BUNDLE_SOURCES = "BUNDLE_SOURCES"

        fun newInstance(): NewsSourcesFragment = NewsSourcesFragment()
    }

    init {
        setHasOptionsMenu(true)
    }

    @InjectPresenter
    lateinit var mPresenter: NewsSourcesPresenter

    private lateinit var mAdapter: NewsSourcesAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = NewsSourcesAdapter(context, ArrayList<Source>())
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recycler_view.hasFixedSize()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_news_sources, menu)
    }

    override fun attachInputListeners() {
        val toolbarActions = RxToolbar.itemClicks(activity.toolbar)
                .subscribe({ mPresenter.onOptionsItemSelected(it) }, Timber::e)

        mDisposable.addAll(toolbarActions)
    }

    override fun detachInputListeners() {
        mDisposable.clear()
    }

    override fun render(model: NewsSourcesModel) {
        when (model) {

        }
    }
}
