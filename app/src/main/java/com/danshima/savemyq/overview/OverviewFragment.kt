package com.danshima.savemyq.overview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danshima.savemyq.R
import com.danshima.savemyq.databinding.FragmentOverviewBinding
import com.danshima.savemyq.di.Injectable
import com.danshima.savemyq.common.ItemDecoration
import com.danshima.savemyq.common.provideViewModel
import com.danshima.savemyq.model.Resource

import javax.inject.Inject


class OverviewFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentOverviewBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: OverviewViewModel

    private lateinit var adapter: OverviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = provideViewModel(viewModelFactory)

        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding.toolbar.title = "Goals"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        adapter = OverviewAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            ItemDecoration(
                resources.getDimension(
                    R.dimen.standard16
                ).toInt()
            )
        )

        binding.refresh.setOnRefreshListener {
            viewModel.fetchGoals(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.savingGoals.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    binding.refresh.isRefreshing = false
                    adapter.submitList(result.value)
                    adapter.notifyDataSetChanged()
                }
                is Resource.Error -> binding.refresh.isRefreshing = false
                is Resource.Loading -> binding.refresh.isRefreshing = true
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchData()
    }
}
