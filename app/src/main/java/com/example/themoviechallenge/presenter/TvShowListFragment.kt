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
import com.highquality.base.presenter.BaseFragment
import com.highquality.base.presenter.BaseViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TvShowListFragment : BaseFragment<FragmentTvShowListBinding>(),
    ViewHolderListener<TvShowModel> {

    private val viewModel: TvShowViewModel by sharedViewModel(clazz = TvShowViewModel::class)
    private lateinit var adapter: TvShowAdapter


    override fun getViewModel(): BaseViewModel = viewModel

    override fun init() {
        setupObserversViewModel()
        if (!this::adapter.isInitialized) {
            viewModel.fetchTvShows(firstTime = true)
        }
        bindData()
    }

    private fun bindData() {
        initRecyclerView()
        binding.layoutAppBar.textViewToolbarTitle.text =
            getString(R.string.fragment_tv_show_list_toolbar_title)
        binding.swiperefresh.setOnRefreshListener {
            viewModel.resetPaginationPageTvShowList()
            adapter.clear()
            viewModel.fetchTvShows(firstTime = true)
        }
        binding.fabGoUp.setOnClickListener {
            binding.recyclerview.layoutManager?.scrollToPosition(0)
        }
        binding.layoutGenericError.textviewTry.setOnClickListener {
            viewModel.fetchTvShows()
        }
        binding.layoutNoConnection.textviewTry.setOnClickListener {
            viewModel.fetchTvShows()
        }
    }

    override fun setupObserversViewModel() {
        viewModel.mutableLoading.observe(this, Observer {
            if (it) {
                binding.mainContainer.visibility = View.GONE
                binding.layoutLoading.root.visibility = View.VISIBLE
            } else {
                binding.mainContainer.visibility = View.VISIBLE
                binding.layoutLoading.root.visibility = View.GONE
            }
        })
        viewModel.mutableConnection.observe(this, Observer {
            showNoConnection(it)
        })
        viewModel.mutableThrowables.observe(this, Observer {
            showGenericError(it != null)
        })
        viewModel.tvShowLiveData.observe(this, { list ->

            if (binding.swiperefresh.isRefreshing) binding.swiperefresh.isRefreshing = false

            if (list.isEmpty()) {
                showEmptyState(true)
            } else {
                showEmptyState(false)
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
        val action =
            TvShowListFragmentDirections.actionTvShowListFragmentToTvShowDetailFragment(item)
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
        if (value) {
            binding.layoutWithoutResults.root.visibility = View.VISIBLE
            binding.mainContainer.visibility = View.GONE
        } else {
            binding.layoutWithoutResults.root.visibility = View.GONE
            binding.mainContainer.visibility = View.VISIBLE
        }
    }

    private fun showGenericError(value: Boolean) {
        if (value) {
            binding.layoutGenericError.root.visibility = View.VISIBLE
            binding.mainContainer.visibility = View.GONE
        } else {
            binding.layoutGenericError.root.visibility = View.GONE
            binding.mainContainer.visibility = View.VISIBLE
        }
    }

    private fun showNoConnection(value: Boolean) {
        if (value) {
            binding.layoutNoConnection.container.visibility = View.GONE
            binding.mainContainer.visibility = View.VISIBLE
        } else {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            binding.layoutNoConnection.container.visibility = View.VISIBLE
            binding.mainContainer.visibility = View.GONE
        }
    }

    override fun observerCommons() {
        //no require observe commons
    }
}