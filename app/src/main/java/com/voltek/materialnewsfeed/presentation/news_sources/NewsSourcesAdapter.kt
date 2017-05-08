package com.voltek.materialnewsfeed.presentation.news_sources

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.vicpin.krealmextensions.save
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.entity.Source
import kotlinx.android.synthetic.main.item_source.view.*

class NewsSourcesAdapter(private val mContext: Context, private var mItems: MutableList<Source>)
    : RecyclerView.Adapter<NewsSourcesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.tv_name
        val description: TextView = view.tv_description
        val category: TextView = view.tv_category
        val country: ImageView = view.im_country
        val enabled: CheckBox = view.cb_enable
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.item_source, parent, false)
        return NewsSourcesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = mItems[position]

        RxView.clicks(holder!!.itemView).subscribe({
            item.isEnabled = !item.isEnabled
            holder.enabled.isChecked = item.isEnabled
            item.save()
        })

        holder.name.text = item.name
        holder.description.text = item.description
        holder.category.text = item.category
        holder.enabled.isChecked = item.isEnabled

        when (item.country) {
            "au" -> Glide.with(mContext).load("").error(R.drawable.ic_australia_24dp).into(holder.country)
            "de" -> Glide.with(mContext).load("").error(R.drawable.ic_germany_24dp).into(holder.country)
            "gb" -> Glide.with(mContext).load("").error(R.drawable.ic_united_kingdom_24dp).into(holder.country)
            "in" -> Glide.with(mContext).load("").error(R.drawable.ic_india_24dp).into(holder.country)
            "it" -> Glide.with(mContext).load("").error(R.drawable.ic_italy_24dp).into(holder.country)
            "us" -> Glide.with(mContext).load("").error(R.drawable.ic_united_states_24dp).into(holder.country)
        }
    }

    override fun getItemCount(): Int = mItems.size

    fun replace(items: List<Source>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }
}
