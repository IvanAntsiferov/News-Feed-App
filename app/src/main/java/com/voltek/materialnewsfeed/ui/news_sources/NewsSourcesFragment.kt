package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Source
import com.voltek.materialnewsfeed.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*
import org.parceler.Parcels

class NewsSourcesFragment : BaseFragment<NewsSourcesContract.View, NewsSourcesContract.Presenter>(),
        NewsSourcesContract.View {

    init {
        setHasOptionsMenu(true)
    }

    companion object {
        const val TAG = "NewsSourcesFragment"
        const val BUNDLE_SOURCES = "BUNDLE_SOURCES"

        val CATEGORIES = arrayOf(
                "business", "entertainment", "gaming", "general", "music",
                "politics", "science-and-nature", "sport", "technology")

        fun newInstance(): NewsSourcesFragment = NewsSourcesFragment()
    }

    private lateinit var mAdapter: NewsSourcesAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mPresenter = NewsSourcesPresenter(activity as NewsSourcesContract.Navigator)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = NewsSourcesAdapter(context, ArrayList<Source>())
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recycler_view.hasFixedSize()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter

        swipe_container.isEnabled = false

        mPresenter?.attach(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val sources = Parcels.wrap(ArrayList(mAdapter.getItems()))
        outState?.putParcelable(BUNDLE_SOURCES, sources)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_news_sources, menu)
    }

    override fun handleLoading() {
        swipe_container.isEnabled = true
        swipe_container.isRefreshing = true
    }

    override fun handleResponse(sources: List<Source>) {
        loadingFinished()
        mAdapter.replaceItems(sources)
    }

    override fun handleError(error: String) {
        loadingFinished()
        tv_empty_state.text = error
    }

    private fun loadingFinished() {
        swipe_container.isRefreshing = false
        swipe_container.isEnabled = false
    }
}
