package com.example.themoviechallenge.presenter


import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.themoviechallenge.databinding.FragmentTvShowDetailBinding
import com.example.themoviechallenge.presenter.viewmodel.TvShowViewModel
import com.highquality.base.BaseFragment
import com.highquality.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TvShowDetailFragment : BaseFragment<FragmentTvShowDetailBinding>() {

    private val viewModel: TvShowViewModel by sharedViewModel(clazz = TvShowViewModel::class)

    override fun getViewModel(): BaseViewModel = viewModel

    override fun init() {

        setupObserversViewModel()
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
        viewModel.tvShowRelatedLiveData.observe(this, {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        })
    }


}