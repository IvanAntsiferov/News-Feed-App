package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.BaseFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment<ListContract.View, ListContract.Presenter>(),
        ListContract.View {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var mAdapter: ListAdapter

    override fun onAttach(context: Context?)  {
        super.onAttach(context)
        mPresenter = ListPresenter(activity as ListContract.Navigator)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = ListAdapter(context, ArrayList<Article>())
        return inflater?.inflate(R.layout.fragment_list, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recycler_view.hasFixedSize()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter

        mPresenter?.attach(this)
    }

    override fun onItemClick(): Observable<Article> = mAdapter.getViewClickedObservable()

    override fun handleResponse(articles: List<Article>) {
        progress_bar.visibility = View.GONE
        mAdapter.addAll(articles)
    }

    override fun handleError(error: String) {
        progress_bar.visibility = View.GONE
        tv_empty_state.text = error
    }
}
