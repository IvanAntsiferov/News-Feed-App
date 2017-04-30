package com.voltek.materialnewsfeed.ui.news_sources

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.ui.BaseFragment
import com.voltek.materialnewsfeed.ui.details.DetailsFragment
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber

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

    @InjectPresenter(type = PresenterType.LOCAL, tag = DetailsFragment.TAG)
    lateinit var mPresenter: NewsSourcesPresenter

    private lateinit var mAdapter: NewsSourcesAdapter

    init {
        setHasOptionsMenu(true)
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
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_news_sources, menu)
    }

    override fun attachInputListeners() {
        val inputEventsFeed = mPresenter.inputEventsFeed()

        val filterActions = RxToolbar.itemClicks(activity.toolbar)
                .filter { !swipe_container.isRefreshing }
                .filter { it.itemId in CATEGORY_ITEMS_IDS }
                .filter { !it.isChecked }
                .subscribe({
                    it.isChecked = true
                    inputEventsFeed.onNext(NewsSourcesEvent.filter(it.title.toString()))
                }, Timber::e)

        mDisposable.addAll(filterActions)
    }

    override fun detachInputListeners() {
        mDisposable.clear()
    }

    override fun render(model: NewsSourcesModel) {
        when (model) {
            is NewsSourcesModel.Loading -> {
                tv_empty_state.visibility = GONE
                tv_empty_state.text = ""

                mAdapter.clear()

                swipe_container.isEnabled = true
                swipe_container.isRefreshing = true
            }
            is NewsSourcesModel.Result -> {
                tv_empty_state.visibility = GONE
                tv_empty_state.text = ""

                mAdapter.replaceItems(model.items)

                swipe_container.isRefreshing = false
                swipe_container.isEnabled = false
            }
            is NewsSourcesModel.Error -> {
                tv_empty_state.text = model.message
                tv_empty_state.visibility = VISIBLE

                mAdapter.clear()

                swipe_container.isRefreshing = false
                swipe_container.isEnabled = false
            }
        }
    }
}
