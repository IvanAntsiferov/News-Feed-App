package com.voltek.materialnewsfeed.ui.news_sources

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.api.Source
import kotlinx.android.synthetic.main.item_source.view.*

class NewsSourcesAdapter(private val mContext: Context, private var mItems: MutableList<Source>)
    : RecyclerView.Adapter<NewsSourcesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.tv_name!!
        val description = view.tv_description!!
        val category = view.tv_category!!
        val country = view.tv_country!!
        val enabled = view.cb_enable!!
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.item_source, parent, false)
        return NewsSourcesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = mItems[position]

        holder?.name?.text = item.name
        holder?.description?.text = item.description
        holder?.category?.text = item.category
        holder?.country?.text = item.country
        holder?.enabled?.isChecked = item.isEnabled
    }

    fun addAll(sources: List<Source>) {
        mItems.addAll(sources)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mItems.size

    fun getItems(): List<Source> = mItems
}
