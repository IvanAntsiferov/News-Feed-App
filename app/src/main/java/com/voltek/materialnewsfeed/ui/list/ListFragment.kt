package com.voltek.materialnewsfeed.ui.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.BaseFragment
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.list.ListContract.ListModel
import com.voltek.materialnewsfeed.ui.list.ListContract.ListView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*

class ListFragment : BaseFragment(),
        ListView {

    companion object {
        const val TAG = "ListFragmentTag"

        fun newInstance() = ListFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: ListPresenter

    private lateinit var mAdapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = ListAdapter(context, ArrayList<Article>())
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recycler_view.hasFixedSize()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter
    }

    override fun attachInputListeners() {
        // Toolbar
        RxToolbar.itemClicks(activity.toolbar)
                .subscribe({
                    when (it.itemId) {
                        R.id.action_news_sources -> mPresenter.notify(Event.OpenNewsSources())
                    }
                })
                .bind()

        // On article click
        mAdapter.getOnItemClickObservable()
                .subscribe({ mPresenter.notify(Event.OpenArticleDetails(it)) })
                .bind()

        // Swipe to refresh
        RxSwipeRefreshLayout.refreshes(swipe_container)
                .subscribe({ mPresenter.notify(Event.Refresh()) })
                .bind()
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: ListModel) {
        swipe_container.isRefreshing = model.loading

        mAdapter.replace(model.articles)

        if (!model.error.isEmpty()) {
            if (model.articles.isEmpty()) {
                tv_empty_state.text = model.error
                tv_empty_state.visibility = VISIBLE
            }
            else {
                tv_empty_state.visibility = GONE
                tv_empty_state.text = ""
                toast(model.error)
            }
        } else {
            tv_empty_state.visibility = GONE
            tv_empty_state.text = ""
        }
    }
}
