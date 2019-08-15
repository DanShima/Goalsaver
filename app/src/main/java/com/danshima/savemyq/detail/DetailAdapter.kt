package com.danshima.savemyq.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danshima.savemyq.data.Feed
import com.danshima.savemyq.databinding.DetailViewItemBinding

class DetailAdapter : ListAdapter<Feed, DetailAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DetailViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed = getItem(position)
        holder.apply {
            bind(feed)
            itemView.tag = feed
        }
    }


    class ViewHolder(private val binding: DetailViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Feed) {
            binding.apply {
                feed = item
                executePendingBindings()
            }
        }
    }
}
