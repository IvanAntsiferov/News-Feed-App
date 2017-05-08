package com.voltek.materialnewsfeed.presentation.details

import android.os.Bundle
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import com.voltek.materialnewsfeed.presentation.BaseFragment
import com.voltek.materialnewsfeed.presentation.Event
import com.voltek.materialnewsfeed.presentation.details.DetailsContract.DetailsView
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.toolbar.*
import org.parceler.Parcels

class DetailsFragment : BaseFragment(),
        DetailsView {

    companion object {
        const val TAG = "DetailsFragment"

        const val ARG_ARTICLE = "ARG_ARTICLE"

        fun newInstance(article: Article): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_ARTICLE, Parcels.wrap(article))
            fragment.arguments = args
            return fragment
        }
    }

    init {
        setHasOptionsMenu(true)
    }

    @InjectPresenter(type = PresenterType.LOCAL, tag = TAG)
    lateinit var mPresenter: DetailsPresenter

    @ProvidePresenter(type = PresenterType.LOCAL, tag = TAG)
    fun providePresenter(): DetailsPresenter =
            DetailsPresenter(Parcels.unwrap(arguments.getParcelable(ARG_ARTICLE)))

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_details, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_details, menu)
    }

    override fun attachInputListeners() {
        RxToolbar.itemClicks(activity.toolbar)
                .subscribe({
                    when (it.itemId) {
                        R.id.action_share -> mPresenter.notify(Event.Share())
                        R.id.action_website -> mPresenter.notify(Event.OpenWebsite())
                    }
                })
                .bind()
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
