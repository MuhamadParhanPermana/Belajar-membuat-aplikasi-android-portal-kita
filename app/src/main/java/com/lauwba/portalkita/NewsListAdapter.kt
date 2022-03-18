package com.lauwba.portalkita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lauwba.portalkita.databinding.ItemNewsListAdapterBinding
import com.lauwba.portalkita.model.DataItem

class NewsListAdapter(
    private val listNews: List<DataItem?>?,
    private val onListItemClick: (DataItem?) -> Unit
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    inner class ViewHolder(val itemNewsListAdapterBinding: ItemNewsListAdapterBinding) :
        RecyclerView.ViewHolder(itemNewsListAdapterBinding.root) {

        fun onBindItem(dataItem: DataItem?) {
            itemNewsListAdapterBinding.datePost.text = dataItem?.postOn
            itemNewsListAdapterBinding.judul.text = dataItem?.jdlNews
            Glide.with(itemNewsListAdapterBinding.root.context)
                .load(dataItem?.fotoNews)
                .into(itemNewsListAdapterBinding.fotoBerita)

            itemNewsListAdapterBinding.root.setOnClickListener {
                onListItemClick(dataItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNewsListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listNews?.get(position))
    }

    override fun getItemCount(): Int {
        return listNews?.size ?: 0
    }
}