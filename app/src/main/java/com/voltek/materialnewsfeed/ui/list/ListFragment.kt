package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment<ListContract.View, ListContract.Presenter>(),
        ListContract.View {

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onAttach(context: Context?)  {
        super.onAttach(context)
        mPresenter = ListPresenter(activity as ListContract.Navigator)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //
    }
}
