package com.danshima.savemyq.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.danshima.savemyq.R
import com.danshima.savemyq.common.bindImageFromUrl
import com.danshima.savemyq.databinding.FragmentDetailBinding
import com.danshima.savemyq.databinding.RulesTypeIconBinding
import com.danshima.savemyq.di.Injectable
import com.danshima.savemyq.overview.OverviewViewModel
import com.danshima.savemyq.common.provideViewModel
import com.danshima.savemyq.databinding.RulesTypeHeaderBinding
import com.danshima.savemyq.model.Feed
import com.danshima.savemyq.model.Resource
import com.danshima.savemyq.model.SavingsRule
import com.danshima.savemyqui.goal.DetailAdapter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class DetailFragment : Fragment(), Injectable {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: DetailAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = provideViewModel(viewModelFactory)
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.statusBar)
        adapter = DetailAdapter()
        binding.recyclerView.adapter = adapter

        bindImageFromUrl(binding.image, args.goal.goalImageURL)
        binding.image.contentDescription = args.goal.name
        binding.detailImageHeader.goal = args.goal
        binding.detailImageHeader.executePendingBindings()

        binding.swipeRefresh.setOnRefreshListener {
            forceReload()
        }

        binding.fab.setOnClickListener {
            Snackbar.make(binding.root, R.string.add_action, Snackbar.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun forceReload() {
        viewModel.fetchRules(true)
        viewModel.fetchFeed(args.goal.id, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.feed.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    result.value.let {
                        if (it.isNotEmpty()) {
                            if (args.goal.id == result.value[0].userId) {
                                adapter.submitList(result.value)
                            }
                            updateSubHeader(result.value)
                        }
                    }
                }
                is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                is Resource.Error -> binding.swipeRefresh.isRefreshing = false
            }
        })
        viewModel.fetchFeed(args.goal.id)
        viewModel.savingRules.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    createRuleEntries(result.value)
                }
                else -> {  Snackbar.make(binding.root, R.string.edit_detail, Snackbar.LENGTH_LONG).show() }
            }
        })
        viewModel.fetchRules()
    }

    private fun updateSubHeader(value: List<Feed>) {
        var totalValue = 0f
        value.forEach {
            totalValue += it.amount
        }
        binding.weeklySavingLayout.weeklySaving = totalValue
        binding.weeklySavingLayout.executePendingBindings()
    }

    private fun createRuleEntries(savingRules: List<SavingsRule>) {
        binding.linearLayout.removeAllViews()
        val inflater = LayoutInflater.from(binding.root.context)
        val headerParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            resources.getDimension(R.dimen.rule_header_height).toInt()
        )
        //   headerParams
        headerParams.setMargins(
            resources.getDimension(R.dimen.standard16).toInt(),
            0,
            resources.getDimension(R.dimen.standard16).toInt(),
            0
        )
        binding.linearLayout.addView(RulesTypeHeaderBinding.inflate(inflater).root, headerParams)

        val ruleSize = resources.getDimension(R.dimen.rule_icon_size).toInt()
        val tinyMargin = resources.getDimension(R.dimen.standard4).toInt()
        val ruleParams = LinearLayout.LayoutParams(
            ruleSize,
            ruleSize
        )

        ruleParams.setMargins(resources.getDimension(R.dimen.standard8).toInt(), tinyMargin, 0, tinyMargin)

        savingRules.forEach {
            RulesTypeIconBinding.inflate(LayoutInflater.from(binding.root.context)).apply {
                this.saving = it
                binding.linearLayout.addView(this.root, ruleParams)
                this.executePendingBindings()
                root.setOnClickListener { _ ->
                    Log.d("Detail", "${it.type} ${it.amount}")
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.title = ""
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                Snackbar.make(binding.root, R.string.edit_detail, Snackbar.LENGTH_LONG).show()
                return true
            }
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }
}
