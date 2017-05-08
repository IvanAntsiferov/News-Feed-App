package com.voltek.materialnewsfeed.presentation.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Article
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_article.view.*
import kotlinx.android.synthetic.main.item_title.view.*

class ListAdapter(private val mContext: android.content.Context, private var mItems: MutableList<com.voltek.materialnewsfeed.data.entity.Article>)
    : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TITLE = 0
        private const val ARTICLE = 1
    }

    private var mOnItemClickSubject: io.reactivex.subjects.PublishSubject<Article> = io.reactivex.subjects.PublishSubject.create()

    fun getOnItemClickObservable(): io.reactivex.Observable<Article> = mOnItemClickSubject

    class ViewHolderTitle(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view) {
        val title: android.widget.TextView = kotlinx.android.synthetic.main.item_title.view.tv_source_title
    }

    class ViewHolderArticle(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view) {
        val image: android.widget.ImageView = kotlinx.android.synthetic.main.item_article.view.im_image
        val title: android.widget.TextView = kotlinx.android.synthetic.main.item_article.view.tv_title
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup?, viewType: Int): android.support.v7.widget.RecyclerView.ViewHolder {
        val layoutInflater = android.view.LayoutInflater.from(parent?.context)
        when (viewType) {
            com.voltek.materialnewsfeed.presentation.ListAdapter.Companion.TITLE -> {
                val view = layoutInflater.inflate(com.voltek.materialnewsfeed.R.layout.item_title, parent, false)
                return com.voltek.materialnewsfeed.presentation.ListAdapter.ViewHolderTitle(view)
            }
            else -> {
                val view = layoutInflater.inflate(com.voltek.materialnewsfeed.R.layout.item_article, parent, false)
                return com.voltek.materialnewsfeed.presentation.ListAdapter.ViewHolderArticle(view)
            }
        }
    }

    override fun onBindViewHolder(holder: android.support.v7.widget.RecyclerView.ViewHolder?, position: Int) {
        when (holder?.itemViewType) {
            com.voltek.materialnewsfeed.presentation.ListAdapter.Companion.TITLE -> bindTitle(holder as com.voltek.materialnewsfeed.presentation.ListAdapter.ViewHolderTitle, position)
            com.voltek.materialnewsfeed.presentation.ListAdapter.Companion.ARTICLE -> bindArticle(holder as com.voltek.materialnewsfeed.presentation.ListAdapter.ViewHolderArticle, position)
        }
    }

    private fun bindTitle(holder: com.voltek.materialnewsfeed.presentation.ListAdapter.ViewHolderTitle, position: Int) {
        val item = mItems[position]
        holder.title.text = item.source
    }

    private fun bindArticle(holder: com.voltek.materialnewsfeed.presentation.ListAdapter.ViewHolderArticle, position: Int) {
        val item = mItems[position]

        com.jakewharton.rxbinding2.view.RxView.clicks(holder.itemView).subscribe({ mOnItemClickSubject.onNext(item) })

        com.bumptech.glide.Glide.with(mContext).load(item.urlToImage).into(holder.image)
        holder.title.text = item.title
    }

    override fun getItemCount(): Int = mItems.size

    override fun getItemViewType(position: Int): Int =
            if (mItems[position].isEmpty()) com.voltek.materialnewsfeed.presentation.ListAdapter.Companion.TITLE else com.voltek.materialnewsfeed.presentation.ListAdapter.Companion.ARTICLE

    fun replace(items: List<com.voltek.materialnewsfeed.data.entity.Article>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }
}
