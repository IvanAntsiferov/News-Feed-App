package com.voltek.materialnewsfeed.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.api.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ListAdapter(val mContext: Context, var mItems: List<Article>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.im_image
        val title = view.tv_title
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_article, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = mItems[position]

        Glide.with(mContext).load(item.urlToImage).into(holder?.image)
        holder?.title?.text = item.title
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}
