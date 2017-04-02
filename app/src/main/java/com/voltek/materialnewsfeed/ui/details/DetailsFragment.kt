package com.voltek.materialnewsfeed.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.api.NewsApi
import com.voltek.materialnewsfeed.ui.BaseFragment
import javax.inject.Inject

class DetailsFragment : BaseFragment<DetailsContract.View, DetailsContract.Presenter>(),
    DetailsContract.View {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    @Inject
    lateinit var mApi: NewsApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MaterialNewsFeedApp.mainComponent.inject(this)
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
