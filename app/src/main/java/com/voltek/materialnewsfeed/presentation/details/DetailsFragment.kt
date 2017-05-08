package com.voltek.materialnewsfeed.presentation.details

import com.voltek.materialnewsfeed.presentation.BaseFragment
import com.voltek.materialnewsfeed.ui.Event
import com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView
import org.parceler.Parcels

class DetailsFragment : BaseFragment(),
        com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsView {

    companion object {
        const val TAG = "DetailsFragment"

        const val ARG_ARTICLE = "ARG_ARTICLE"

        fun newInstance(article: com.voltek.materialnewsfeed.data.entity.Article): com.voltek.materialnewsfeed.presentation.DetailsFragment {
            val fragment = com.voltek.materialnewsfeed.presentation.DetailsFragment()
            val args = android.os.Bundle()
            args.putParcelable(com.voltek.materialnewsfeed.presentation.DetailsFragment.Companion.ARG_ARTICLE, org.parceler.Parcels.wrap(article))
            fragment.arguments = args
            return fragment
        }
    }

    init {
        setHasOptionsMenu(true)
    }

    @com.arellomobile.mvp.presenter.InjectPresenter(type = com.arellomobile.mvp.presenter.PresenterType.LOCAL, tag = com.voltek.materialnewsfeed.presentation.DetailsFragment.Companion.TAG)
    lateinit var mPresenter: DetailsPresenter

    @com.arellomobile.mvp.presenter.ProvidePresenter(type = com.arellomobile.mvp.presenter.PresenterType.LOCAL, tag = com.voltek.materialnewsfeed.presentation.DetailsFragment.Companion.TAG)
    fun providePresenter(): DetailsPresenter =
            DetailsPresenter(Parcels.unwrap(arguments.getParcelable(ARG_ARTICLE)))

    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        return inflater?.inflate(com.voltek.materialnewsfeed.R.layout.fragment_details, container, false)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?, inflater: android.view.MenuInflater?) {
        inflater?.inflate(com.voltek.materialnewsfeed.R.menu.menu_fragment_details, menu)
    }

    override fun attachInputListeners() {
        com.jakewharton.rxbinding2.support.v7.widget.RxToolbar.itemClicks(kotlinx.android.synthetic.main.toolbar.toolbar)
                .subscribe({
                    when (it.itemId) {
                        com.voltek.materialnewsfeed.R.id.action_share -> mPresenter.notify(com.voltek.materialnewsfeed.ui.Event.Share())
                        com.voltek.materialnewsfeed.R.id.action_website -> mPresenter.notify(com.voltek.materialnewsfeed.ui.Event.OpenWebsite())
                    }
                })
                .bind()
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: com.voltek.materialnewsfeed.ui.details.DetailsContract.DetailsModel) {
        if (model.articleLoaded) {
            com.bumptech.glide.Glide.with(context).load(model.urlToImage).into(kotlinx.android.synthetic.main.fragment_details.im_image)
            kotlinx.android.synthetic.main.fragment_details.tv_author.text = model.author
            kotlinx.android.synthetic.main.fragment_details.tv_title.text = model.title
            kotlinx.android.synthetic.main.fragment_details.tv_published_at.text = model.publishedAt
            kotlinx.android.synthetic.main.fragment_details.tv_description.text = model.description

            kotlinx.android.synthetic.main.fragment_details.tv_message.text = ""
        } else {
            kotlinx.android.synthetic.main.fragment_details.im_image.setImageResource(android.R.color.transparent)
            kotlinx.android.synthetic.main.fragment_details.tv_author.text = ""
            kotlinx.android.synthetic.main.fragment_details.tv_title.text = ""
            kotlinx.android.synthetic.main.fragment_details.tv_published_at.text = ""
            kotlinx.android.synthetic.main.fragment_details.tv_description.text = ""

            kotlinx.android.synthetic.main.fragment_details.tv_message.text = getString(com.voltek.materialnewsfeed.R.string.error_empty_details)
        }
    }
}
