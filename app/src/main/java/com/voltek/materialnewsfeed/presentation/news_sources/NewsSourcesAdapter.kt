package com.voltek.materialnewsfeed.presentation.news_sources

import com.vicpin.krealmextensions.save

class NewsSourcesAdapter(private val mContext: android.content.Context, private var mItems: MutableList<com.voltek.materialnewsfeed.data.entity.Source>)
    : android.support.v7.widget.RecyclerView.Adapter<NewsSourcesAdapter.ViewHolder>() {

    class ViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view) {
        val name: android.widget.TextView = kotlinx.android.synthetic.main.item_source.view.tv_name
        val description: android.widget.TextView = kotlinx.android.synthetic.main.item_source.view.tv_description
        val category: android.widget.TextView = kotlinx.android.synthetic.main.item_source.view.tv_category
        val country: android.widget.ImageView = kotlinx.android.synthetic.main.item_source.view.im_country
        val enabled: android.widget.CheckBox = kotlinx.android.synthetic.main.item_source.view.cb_enable
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup?, viewType: Int): com.voltek.materialnewsfeed.presentation.NewsSourcesAdapter.ViewHolder {
        val layoutInflater = android.view.LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(com.voltek.materialnewsfeed.R.layout.item_source, parent, false)
        return com.voltek.materialnewsfeed.presentation.NewsSourcesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: com.voltek.materialnewsfeed.presentation.NewsSourcesAdapter.ViewHolder?, position: Int) {
        val item = mItems[position]

        com.jakewharton.rxbinding2.view.RxView.clicks(holder!!.itemView).subscribe({
            item.isEnabled = !item.isEnabled
            holder.enabled.isChecked = item.isEnabled
            item.save()
        })

        holder.name.text = item.name
        holder.description.text = item.description
        holder.category.text = item.category
        holder.enabled.isChecked = item.isEnabled

        when (item.country) {
            "au" -> com.bumptech.glide.Glide.with(mContext).load("").error(com.voltek.materialnewsfeed.R.drawable.ic_australia_24dp).into(holder.country)
            "de" -> com.bumptech.glide.Glide.with(mContext).load("").error(com.voltek.materialnewsfeed.R.drawable.ic_germany_24dp).into(holder.country)
            "gb" -> com.bumptech.glide.Glide.with(mContext).load("").error(com.voltek.materialnewsfeed.R.drawable.ic_united_kingdom_24dp).into(holder.country)
            "in" -> com.bumptech.glide.Glide.with(mContext).load("").error(com.voltek.materialnewsfeed.R.drawable.ic_india_24dp).into(holder.country)
            "it" -> com.bumptech.glide.Glide.with(mContext).load("").error(com.voltek.materialnewsfeed.R.drawable.ic_italy_24dp).into(holder.country)
            "us" -> com.bumptech.glide.Glide.with(mContext).load("").error(com.voltek.materialnewsfeed.R.drawable.ic_united_states_24dp).into(holder.country)
        }
    }

    override fun getItemCount(): Int = mItems.size

    fun replace(items: List<com.voltek.materialnewsfeed.data.entity.Source>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }
}
