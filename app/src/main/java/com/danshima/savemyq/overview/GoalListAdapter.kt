package com.danshima.savemyq.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danshima.savemyq.R
import com.danshima.savemyq.data.SavingGoal
import com.danshima.savemyq.databinding.GridViewItemBinding


class GoalListAdapter(val clickListener: ItemClickListener<SavingGoal>) : ListAdapter<SavingGoal, GoalListAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<SavingGoal>() {
            override fun areItemsTheSame(oldItem: SavingGoal, newItem: SavingGoal): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: SavingGoal, newItem: SavingGoal): Boolean =
                oldItem == newItem
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            GridViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    inner class ViewHolder(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(savingGoal: SavingGoal) {
            itemView.setOnClickListener {
                clickListener.onItemClicked(savingGoal)
            }
            binding.goal = savingGoal
            binding.executePendingBindings()
        }
    }
}

interface ItemClickListener<T> {
    fun onItemClicked(item: T)
}