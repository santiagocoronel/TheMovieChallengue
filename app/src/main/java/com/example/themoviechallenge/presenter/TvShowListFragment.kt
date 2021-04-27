package com.example.themoviechallenge.presenter

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.themoviechallenge.R
import com.example.themoviechallenge.databinding.FragmentTvShowListBinding
import com.example.themoviechallenge.presenter.adapter.TvShowAdapter
import com.example.themoviechallenge.presenter.adapter.ViewHolderListener
import com.example.themoviechallenge.presenter.model.TvShowModel
import com.example.themoviechallenge.presenter.viewmodel.TvShowViewModel
import com.highquality.base.BaseFragment
import com.highquality.base.BaseViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TvShowListFragment : BaseFragment<FragmentTvShowListBinding>(),
    ViewHolderListener<TvShowModel> {

    private val viewModel: TvShowViewModel by sharedViewModel(clazz = TvShowViewModel::class)
    private lateinit var adapter: TvShowAdapter


    override fun getViewModel(): BaseViewModel = viewModel

    override fun init() {
        setupObserversViewModel()
        viewModel.fetchTvShows(firstTime = true)
        bindData()
    }

    private fun bindData() {
        initRecyclerView()
        binding.layoutAppBar.textViewToolbarTitle.text = "Tv Show Popular"
        binding.swiperefresh.setOnRefreshListener {
            viewModel.resetPagination()
            adapter.clear()
            viewModel.fetchTvShows(firstTime = true)
        }
        binding.fabGoUp.setOnClickListener {
            binding.recyclerview.layoutManager?.scrollToPosition(0)
        }
    }

    override fun setupObserversViewModel() {
        viewModel.mutableLoading.observe(this, Observer {
            if (it) {
                binding.container.rootView.visibility = View.GONE
                binding.layoutLoading.root.visibility = View.VISIBLE
            } else {
                binding.container.rootView.visibility = View.VISIBLE
                binding.layoutLoading.root.visibility = View.GONE
            }
        })
        viewModel.tvShowLiveData.observe(this, { list ->

            if (binding.swiperefresh.isRefreshing) binding.swiperefresh.isRefreshing = false

            if (list.isEmpty()) {
                //show empty state
            } else {
                adapter.removeLoading()
                adapter.addAll(list)
                adapter.notifyDataSetChanged()
                binding.recyclerview.post {
                    adapter.isLoading = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        adapter = TvShowAdapter(listener = this, recyclerView = binding.recyclerview)
        binding.recyclerview.adapter = adapter
        AnimationUtils.loadAnimation(requireContext(), R.anim.animation_three)
    }

    override fun onClick(item: TvShowModel, position: Int) {
        val action = TvShowListFragmentDirections.actionTvShowListFragmentToTvShowDetailFragment()
        findNavController().navigate(action)
    }

    override fun onLoadMore() {
        binding.recyclerview.post {
            adapter.addLoading()
            adapter.notifyItemInserted(adapter.totalItemCount - 1)

            val job = Job()
            val scope = CoroutineScope(Dispatchers.Main + job)
            scope.async {
                delay(1500)
                viewModel.fetchTvShows()
            }
        }
    }

    override fun firstPositionIsVisible(value: Boolean) {
        if (!value) {
            binding.fabGoUp.visibility = View.VISIBLE
        } else {
            binding.fabGoUp.visibility = View.GONE
        }
    }

    private fun showEmptyState(value: Boolean) {

    }

}