package com.voltek.materialnewsfeed.presentation.list

import android.view.View.GONE
import android.view.View.VISIBLE
import com.voltek.materialnewsfeed.presentation.BaseFragment
import com.voltek.materialnewsfeed.ui.list.ListContract.ListModel
import com.voltek.materialnewsfeed.ui.list.ListContract.ListView

class ListFragment : BaseFragment(),
        com.voltek.materialnewsfeed.ui.list.ListContract.ListView {

    companion object {
        const val TAG = "ListFragment"

        fun newInstance() = com.voltek.materialnewsfeed.presentation.ListFragment()
    }

    @com.arellomobile.mvp.presenter.InjectPresenter
    lateinit var mPresenter: ListPresenter

    private lateinit var mAdapter: com.voltek.materialnewsfeed.ui.list.ListAdapter

    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        mAdapter = com.voltek.materialnewsfeed.ui.list.ListAdapter(context, ArrayList())
        return inflater?.inflate(com.voltek.materialnewsfeed.R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: android.view.View?, savedInstanceState: android.os.Bundle?) {
        kotlinx.android.synthetic.main.fragment_list.recycler_view.hasFixedSize()
        kotlinx.android.synthetic.main.fragment_list.recycler_view.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
        kotlinx.android.synthetic.main.fragment_list.recycler_view.adapter = mAdapter
    }

    override fun attachInputListeners() {
        // Toolbar
        com.jakewharton.rxbinding2.support.v7.widget.RxToolbar.itemClicks(kotlinx.android.synthetic.main.toolbar.toolbar)
                .subscribe({
                    when (it.itemId) {
                        com.voltek.materialnewsfeed.R.id.action_news_sources -> mPresenter.notify(com.voltek.materialnewsfeed.ui.Event.OpenNewsSources())
                    }
                })
                .bind()

        // On article click
        mAdapter.getOnItemClickObservable()
                .subscribe({ mPresenter.notify(com.voltek.materialnewsfeed.ui.Event.OpenArticleDetails(it)) })
                .bind()

        // Swipe to refresh
        com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout.refreshes(kotlinx.android.synthetic.main.fragment_list.swipe_container)
                .subscribe({ mPresenter.notify(com.voltek.materialnewsfeed.ui.Event.Refresh()) })
                .bind()
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: com.voltek.materialnewsfeed.ui.list.ListContract.ListModel) {
        kotlinx.android.synthetic.main.fragment_list.swipe_container.isRefreshing = model.loading

        mAdapter.replace(model.articles)

        if (!model.message.isEmpty()) {
            if (model.articles.isEmpty()) {
                kotlinx.android.synthetic.main.fragment_list.tv_message.text = model.message
                kotlinx.android.synthetic.main.fragment_list.tv_message.visibility = VISIBLE
            } else {
                kotlinx.android.synthetic.main.fragment_list.tv_message.visibility = GONE
                kotlinx.android.synthetic.main.fragment_list.tv_message.text = ""
                toast(model.message)
            }
        } else {
            kotlinx.android.synthetic.main.fragment_list.tv_message.visibility = GONE
            kotlinx.android.synthetic.main.fragment_list.tv_message.text = ""
        }
    }
}
