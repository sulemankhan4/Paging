package com.droom.pagingcodelab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droom.pagingcodelab.R
import com.droom.pagingcodelab.model.NewsModel

class NewsAdapter(private val mCtx: Context) :
    PagingDataAdapter<NewsModel, NewsAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.item_news, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            Glide.with(mCtx)
                .load(item.images)
                .into(holder.imageView)
            holder.textView.text = item.heading
        } else {
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show()
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView: TextView

        init {
            imageView = itemView.findViewById(R.id.iv_news)
            textView = itemView.findViewById(R.id.tv_heading)
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsModel> =
            object : DiffUtil.ItemCallback<NewsModel>() {
                override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                    return oldItem._id === newItem._id
                }

                override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                    return oldItem == newItem
                }
            }
    }
}