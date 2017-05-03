package com.voltek.materialnewsfeed.ui.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.BaseFragment
import com.voltek.materialnewsfeed.ui.list.ListContract.ListModel
import com.voltek.materialnewsfeed.ui.list.ListContract.ListView
import kotlinx.android.synthetic.main.fragment_list.*

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
        //
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: ListModel) {
        //
    }
}
