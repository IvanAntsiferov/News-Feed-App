package com.voltek.materialnewsfeed.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Article
import com.voltek.materialnewsfeed.ui.BaseFragment
import org.parceler.Parcels
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment<DetailsContract.View, DetailsContract.Presenter>(),
    DetailsContract.View {

    companion object {
        val ARG_ARTICLE = "ARG_ARTICLE"

        val TAG = "DetailsFragmentTag"

        fun newInstance(article: Article): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_ARTICLE, Parcels.wrap(article))
            detailsFragment.arguments = args
            return detailsFragment
        }
    }

    init {
        retainInstance = true
    }

    override fun onAttach(context: Context?)  {
        super.onAttach(context)
        mPresenter = DetailsPresenter(Parcels.unwrap(arguments.getParcelable(ARG_ARTICLE)))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_details, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mPresenter?.attach(this)
    }

    override fun showArticle(article: Article) {
        Glide.with(context).load(article.urlToImage).into(im_image)
        tv_author.text = article.author
        tv_title.text = article.title
        tv_date.text = article.publishedAt
        tv_description.text = article.description
    }
}
