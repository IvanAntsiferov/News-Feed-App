package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.BaseFragment

class NewsSourcesFragment : BaseFragment<NewsSourcesContract.View, NewsSourcesContract.Presenter>(),
        NewsSourcesContract.View {

    companion object {
        const val TAG = "NewsSourcesFragment"

        fun newInstance(): NewsSourcesFragment = NewsSourcesFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mPresenter = NewsSourcesPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter?.attach(this, savedInstanceState)
    }
}
