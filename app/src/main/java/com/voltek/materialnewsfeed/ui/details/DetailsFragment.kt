package com.voltek.materialnewsfeed.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.ui.BaseFragment

class DetailsFragment : BaseFragment<DetailsContract.View, DetailsContract.Presenter>(),
    DetailsContract.View {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    override fun onAttach(context: Context?)  {
        super.onAttach(context)
        mPresenter = DetailsPresenter(activity as DetailsContract.Navigator)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_details, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //
        mPresenter?.attach(this)
    }
}
