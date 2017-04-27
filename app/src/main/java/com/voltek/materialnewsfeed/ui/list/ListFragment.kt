package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.networking.Article
import com.voltek.materialnewsfeed.mvp_deprecated.BaseFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import org.parceler.Parcels

class ListFragment : BaseFragment<ListContract.View, ListContract.Presenter>(),
        ListContract.View {

    companion object {
        const val TAG = "ListFragmentTag"
        const val BUNDLE_ARTICLES = "BUNDLE_ARTICLES"

        fun newInstance() = ListFragment()
    }

    private lateinit var mAdapter: ListAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mPresenter = ListPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = ListAdapter(context, ArrayList<Article>())
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recycler_view.hasFixedSize()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter

        mPresenter?.attach(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val articles = Parcels.wrap(ArrayList(mAdapter.getItems()))
        outState?.putParcelable(BUNDLE_ARTICLES, articles)
        super.onSaveInstanceState(outState)
    }

    override fun toolbarClicks(): Observable<MenuItem> = RxToolbar.itemClicks(activity.toolbar)

    override fun onSwipeToRefresh(): Observable<Unit> = RxSwipeRefreshLayout.refreshes(swipe_container).map { }

    override fun onItemClick(): Observable<Article> = mAdapter.getViewClickedObservable()

    override fun handleLoading() {
        swipe_container.isRefreshing = true
        tv_empty_state.visibility = GONE
        tv_empty_state.text = ""
        mAdapter.clear()
    }

    override fun handleResponse(articles: List<Article>) {
        loadingFinished()
        mAdapter.addAll(articles)
    }

    override fun handleError(message: String) {
        loadingFinished()
        if (mAdapter.itemCount == 0) {
            tv_empty_state.text = message
            tv_empty_state.visibility = VISIBLE
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadingFinished() {
        swipe_container.isRefreshing = false
    }
}
