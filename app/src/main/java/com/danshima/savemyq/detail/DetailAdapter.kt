package com.danshima.savemyqui.goal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danshima.savemyq.databinding.DetailViewItemBinding
import com.danshima.savemyq.model.Feed

class DetailAdapter : ListAdapter<Feed, DetailAdapter.ViewHolder>(FeedDiffCallback()) {


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


    class ViewHolder(val binding: DetailViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Feed) {
            binding.apply {
                feed = item
                executePendingBindings()
            }
        }

    }

}


private class FeedDiffCallback : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }

}