package com.example.themoviechallenge.presenter


import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.themoviechallenge.R
import com.example.themoviechallenge.databinding.FragmentTvShowDetailBinding
import com.example.themoviechallenge.presenter.adapter.TvShowRelatedAdapter
import com.example.themoviechallenge.presenter.adapter.ViewHolderListener
import com.example.themoviechallenge.presenter.model.TvShowModel
import com.example.themoviechallenge.presenter.viewmodel.TvShowViewModel
import com.example.themoviechallenge.base.presenter.BaseFragment
import com.example.themoviechallenge.base.presenter.BaseViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TvShowDetailFragment : BaseFragment<FragmentTvShowDetailBinding>(),
    ViewHolderListener<TvShowModel> {

    private val viewModel: TvShowViewModel by sharedViewModel(clazz = TvShowViewModel::class)
    private val navArgs: TvShowDetailFragmentArgs by navArgs()
    private lateinit var adapter: TvShowRelatedAdapter


    override fun getViewModel(): BaseViewModel = viewModel

    override fun init() {
        setupObserversViewModel()
        if (!this::adapter.isInitialized) {
            viewModel.fetchTvShowsRelated(navArgs.tvShowModel.id.toString())
        }
        bindData()
    }

    private fun bindData() {
        val item = navArgs.tvShowModel
        with(binding) {
            Glide.with(imageview)
                .load("https://image.tmdb.org/t/p/w500/${item.posterPath}")
                .centerInside()
                .into(imageview)

            layoutAppBar.textViewToolbarTitle.text = item.name

            textviewOverview.text = item.overview

            textviewRate.text = "${item.voteAverage}"
        }
        initRecyclerView()
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
        viewModel.tvShowRelatedLiveData.observe(this, { list ->
            val startPosition = adapter.itemCount - 1

            adapter.addAll(list)
            adapter.notifyItemRangeChanged(startPosition, startPosition + list.size)
            binding.recyclerview.post {
                adapter.isLoading = false
            }

        })
    }

    private fun initRecyclerView() {
        adapter = TvShowRelatedAdapter(listener = this, recyclerView = binding.recyclerview)
        binding.recyclerview.adapter = adapter
        AnimationUtils.loadAnimation(requireContext(), R.anim.animation_three)
    }

    override fun onClick(item: TvShowModel, position: Int) {
        val action = TvShowDetailFragmentDirections.actionTvShowDetailFragmentSelf(item)
        findNavController().navigate(action)
    }

    override fun onLoadMore() {
        binding.recyclerview.post {

            val job = Job()
            val scope = CoroutineScope(Dispatchers.Main + job)
            scope.async {
                viewModel.fetchTvShowsRelated(navArgs.tvShowModel.id.toString())
            }
        }
    }

    override fun firstPositionIsVisible(value: Boolean) {

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