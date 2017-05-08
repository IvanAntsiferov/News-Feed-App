package com.voltek.materialnewsfeed.presentation.news_sources

import com.voltek.materialnewsfeed.data.entity.Source
import com.voltek.materialnewsfeed.presentation.BaseFragment
import com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView

class NewsSourcesFragment : BaseFragment(),
        com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesView {

    companion object {
        const val TAG = "NewsSourcesFragment"

        private val CATEGORY_ITEMS_IDS = arrayOf(
                com.voltek.materialnewsfeed.R.id.action_all,
                com.voltek.materialnewsfeed.R.id.action_enabled,
                com.voltek.materialnewsfeed.R.id.action_business,
                com.voltek.materialnewsfeed.R.id.action_entertainment,
                com.voltek.materialnewsfeed.R.id.action_gaming,
                com.voltek.materialnewsfeed.R.id.action_general,
                com.voltek.materialnewsfeed.R.id.action_music,
                com.voltek.materialnewsfeed.R.id.action_politics,
                com.voltek.materialnewsfeed.R.id.action_science_and_nature,
                com.voltek.materialnewsfeed.R.id.action_sport,
                com.voltek.materialnewsfeed.R.id.action_technology
        )

        fun newInstance(): com.voltek.materialnewsfeed.presentation.NewsSourcesFragment = com.voltek.materialnewsfeed.presentation.NewsSourcesFragment()
    }

    @com.arellomobile.mvp.presenter.InjectPresenter
    lateinit var mPresenter: NewsSourcesPresenter

    private lateinit var mAdapter: NewsSourcesAdapter

    private var mCategory: Int = com.voltek.materialnewsfeed.R.id.action_all

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        mAdapter = NewsSourcesAdapter(context, ArrayList<Source>())
        return inflater?.inflate(com.voltek.materialnewsfeed.R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: android.view.View?, savedInstanceState: android.os.Bundle?) {
        kotlinx.android.synthetic.main.fragment_list.recycler_view.hasFixedSize()
        kotlinx.android.synthetic.main.fragment_list.recycler_view.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
        kotlinx.android.synthetic.main.fragment_list.recycler_view.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?, inflater: android.view.MenuInflater?) {
        inflater?.inflate(com.voltek.materialnewsfeed.R.menu.menu_fragment_news_sources, menu)
        menu?.findItem(mCategory)?.isChecked = true
    }

    override fun onPrepareOptionsMenu(menu: android.view.Menu?) {
        menu?.findItem(mCategory)?.isChecked = true
    }

    override fun attachInputListeners() {
        com.jakewharton.rxbinding2.support.v7.widget.RxToolbar.itemClicks(kotlinx.android.synthetic.main.toolbar.toolbar)
                .filter { !kotlinx.android.synthetic.main.fragment_list.swipe_container.isRefreshing }
                .subscribe({
                    when (it.itemId) {
                        in com.voltek.materialnewsfeed.presentation.NewsSourcesFragment.Companion.CATEGORY_ITEMS_IDS -> {
                            if (!it.isChecked) {
                                mPresenter.notify(
                                        com.voltek.materialnewsfeed.ui.Event.FilterSources(it.title.toString(), it.itemId)
                                )
                            }
                        }
                        com.voltek.materialnewsfeed.R.id.action_refresh -> mPresenter.notify(com.voltek.materialnewsfeed.ui.Event.Refresh())
                    }
                })
                .bind()
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: com.voltek.materialnewsfeed.ui.news_sources.NewsSourcesContract.NewsSourcesModel) {
        kotlinx.android.synthetic.main.fragment_list.swipe_container.isEnabled = model.loading
        kotlinx.android.synthetic.main.fragment_list.swipe_container.isRefreshing = model.loading

        mAdapter.replace(model.sources)

        mCategory = model.categoryId
        activity.invalidateOptionsMenu()

        when (model.categoryId) {
            com.voltek.materialnewsfeed.R.id.action_all -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_all)
            com.voltek.materialnewsfeed.R.id.action_enabled -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_enabled)
            com.voltek.materialnewsfeed.R.id.action_business -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_business)
            com.voltek.materialnewsfeed.R.id.action_entertainment -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_entertainment)
            com.voltek.materialnewsfeed.R.id.action_gaming -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_gaming)
            com.voltek.materialnewsfeed.R.id.action_general -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_general)
            com.voltek.materialnewsfeed.R.id.action_music -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_music)
            com.voltek.materialnewsfeed.R.id.action_politics -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_politics)
            com.voltek.materialnewsfeed.R.id.action_science_and_nature -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_science_and_nature)
            com.voltek.materialnewsfeed.R.id.action_sport -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_sport)
            com.voltek.materialnewsfeed.R.id.action_technology -> activity.title = getString(com.voltek.materialnewsfeed.R.string.category_technology)
        }

        if (!model.message.isEmpty()) {
            if (model.sources.isEmpty()) {
                kotlinx.android.synthetic.main.fragment_list.tv_message.text = model.message
                kotlinx.android.synthetic.main.fragment_list.tv_message.visibility = android.view.View.VISIBLE
            } else {
                kotlinx.android.synthetic.main.fragment_list.tv_message.visibility = android.view.View.GONE
                kotlinx.android.synthetic.main.fragment_list.tv_message.text = ""
                toast(model.message)
            }
        } else {
            kotlinx.android.synthetic.main.fragment_list.tv_message.visibility = android.view.View.GONE
            kotlinx.android.synthetic.main.fragment_list.tv_message.text = ""
        }
    }
}
