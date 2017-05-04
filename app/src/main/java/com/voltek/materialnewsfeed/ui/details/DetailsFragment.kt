package com.voltek.materialnewsfeed.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.ui.BaseFragment
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView
import kotlinx.android.synthetic.main.fragment_details.*
import org.parceler.Parcels

class DetailsFragment : BaseFragment(),
        DetailsView {

    companion object {
        const val TAG = "DetailsFragmentTag"

        const val ARG_ARTICLE = "ARG_ARTICLE"

        fun newInstance(article: Article): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_ARTICLE, Parcels.wrap(article))
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter(type = PresenterType.LOCAL, tag = TAG)
    lateinit var mPresenter: DetailsPresenter

    @ProvidePresenter(type = PresenterType.LOCAL, tag = TAG)
    fun providePresenter(): DetailsPresenter =
            DetailsPresenter(Parcels.unwrap(arguments.getParcelable(ARG_ARTICLE)))

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_details, container, false)
    }

    override fun attachInputListeners() {
        //
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: DetailsContract.DetailsModel) {
        if (model.articleLoaded) {
            Glide.with(context).load(model.urlToImage).into(im_image)
            tv_author.text = model.author
            tv_title.text = model.title
            tv_published_at.text = model.publishedAt
            tv_description.text = model.description

            tv_message.text = ""
        } else {
            im_image.setImageResource(android.R.color.transparent)
            tv_author.text = ""
            tv_title.text = ""
            tv_published_at.text = ""
            tv_description.text = ""

            tv_message.text = getString(R.string.error_empty_details)
        }
    }
}
