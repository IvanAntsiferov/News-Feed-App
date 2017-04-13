package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Source
import com.voltek.materialnewsfeed.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*

class NewsSourcesFragment : BaseFragment<NewsSourcesContract.View, NewsSourcesContract.Presenter>(),
        NewsSourcesContract.View {

    companion object {
        const val TAG = "NewsSourcesFragment"

        fun newInstance(): NewsSourcesFragment = NewsSourcesFragment()
    }

    private lateinit var mAdapter: NewsSourcesAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mPresenter = NewsSourcesPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = NewsSourcesAdapter(context, ArrayList<Source>())
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter?.attach(this, savedInstanceState)
    }

    override fun handleResponse(sources: List<Source>) {
        progress_bar.visibility = View.GONE
        //mAdapter.addAll(sources)
    }

    override fun handleError(error: String) {
        progress_bar.visibility = View.GONE
        tv_empty_state.text = error
    }
}
