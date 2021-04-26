package com.example.themoviechallenge.presenter.viewmodel

import com.example.themoviechallenge.domain.GetRelatedTvShowUseCase
import com.example.themoviechallenge.domain.GetTvShowUseCase
import com.highquality.base.BaseViewModel

class TvShowViewModel(
    private val getRelatedTvShowUseCase: GetRelatedTvShowUseCase,
    private val getTvShowUseCase: GetTvShowUseCase
) : BaseViewModel() {



}