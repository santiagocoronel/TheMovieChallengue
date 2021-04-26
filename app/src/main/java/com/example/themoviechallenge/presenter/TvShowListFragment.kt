package com.example.themoviechallenge.presenter

import com.example.themoviechallenge.databinding.FragmentTvShowListBinding
import com.example.themoviechallenge.presenter.viewmodel.TvShowViewModel
import com.highquality.base.BaseFragment
import com.highquality.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TvShowListFragment : BaseFragment<FragmentTvShowListBinding>() {

    private val viewModel: TvShowViewModel by sharedViewModel(clazz = TvShowViewModel::class)

    override fun getViewModel(): BaseViewModel = viewModel

    override fun init() {

        setupObserversViewModel()
    }

    override fun setupObserversViewModel() {

    }


}