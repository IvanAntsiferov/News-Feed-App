package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.BaseFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_list.*
import org.parceler.Parcels

class ListFragment : BaseFragment<ListContract.View, ListContract.Presenter>(),
        ListContract.View {

    companion object {
        const val TAG = "ListFragmentTag"
        const val BUNDLE_ARTICLES = "BUNDLE_ARTICLES"

        fun newInstance() = ListFragment()
    }

    private lateinit var mAdapter: ListAdapter

    override fun onAttach(context: Context?)  {
        super.onAttach(context)
        mPresenter = ListPresenter(activity as ListContract.Navigator)

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

    override fun onSwipeToRefresh(): Observable<Unit> = RxSwipeRefreshLayout.refreshes(swipe_container).map {  }

    override fun onItemClick(): Observable<Article> = mAdapter.getViewClickedObservable()

    override fun handleLoading() {
        swipe_container.isRefreshing = true
    }

    override fun handleResponse(articles: List<Article>) {
        loadingFinished()
        mAdapter.clear()
        mAdapter.addAll(articles)
    }

    override fun handleError(error: String) {
        loadingFinished()
        tv_empty_state.text = error
    }

    private fun loadingFinished() {
        swipe_container.isRefreshing = false
    }
}
