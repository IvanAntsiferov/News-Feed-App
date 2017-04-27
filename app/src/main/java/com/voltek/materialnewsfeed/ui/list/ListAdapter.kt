package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.networking.Article
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_article.view.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject

class ListAdapter(private val mContext: Context, private var mItems: MutableList<Article>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var mViewClickSubject: PublishSubject<Article> = PublishSubject.create<Article>()

    fun getViewClickedObservable(): Observable<Article> = mViewClickSubject

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.im_image!!
        val title = view.tv_title!!
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = mItems[position]

        RxView.clicks(holder!!.itemView).subscribe({ mViewClickSubject.onNext(item) })

        Glide.with(mContext).load(item.urlToImage).into(holder.image)
        holder.title.text = item.title
    }

    override fun getItemCount(): Int = mItems.size

    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
    }

    fun addAll(articles: List<Article>) {
        mItems.addAll(articles)
        notifyDataSetChanged()
    }

    fun getItems(): List<Article> = mItems
}
