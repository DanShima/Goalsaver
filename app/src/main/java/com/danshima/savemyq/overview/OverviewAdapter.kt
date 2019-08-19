package com.danshima.savemyq.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danshima.savemyq.databinding.GridViewItemBinding
import com.danshima.savemyq.model.SavingsGoal


class OverviewAdapter(private val context: Fragment) : ListAdapter<SavingsGoal, OverviewAdapter.ViewHolder>(GoalDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goal = getItem(position)
        holder.apply {
            bind(createOnClickListener(goal), goal)
            itemView.tag = goal
        }
    }

    private fun createOnClickListener(goal: SavingsGoal): View.OnClickListener {
        return View.OnClickListener {
            val direction = OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(goal)
            context.findNavController().navigate(direction)
        }
    }

    class ViewHolder(val binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: SavingsGoal) {
            binding.apply {
                clickListener = listener
                goal = item
                executePendingBindings()
            }
        }
    }
}

private class GoalDiffCallback : DiffUtil.ItemCallback<SavingsGoal>() {
    override fun areItemsTheSame(oldItem: SavingsGoal, newItem: SavingsGoal): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: SavingsGoal, newItem: SavingsGoal): Boolean = oldItem == newItem
}