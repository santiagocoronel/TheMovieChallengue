package com.example.themoviechallenge.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviechallenge.domain.GetRelatedTvShowUseCase
import com.example.themoviechallenge.domain.GetTvShowUseCase
import com.example.themoviechallenge.domain.model.TvShow
import com.highquality.base.BaseViewModel
import com.highquality.base.Response
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.lang.Exception

class TvShowViewModel(
    private val getRelatedTvShowUseCase: GetRelatedTvShowUseCase,
    private val getTvShowUseCase: GetTvShowUseCase
) : BaseViewModel() {

    //region tv shows list
    private val mutableTvShowList: MutableLiveData<List<TvShow>> = MutableLiveData()
    val tvShowLiveData: LiveData<List<TvShow>> get() = mutableTvShowList
    //endregion

    fun fetchTvShows() {
        viewModelScope.launch {

            getTvShowUseCase.bind(language = null, page = 1)
            executeSimpleUseCase(getTvShowUseCase).single().collect {
                when (it) {
                    is Response.Success<List<TvShow>> -> {
                        mutableTvShowList.value = it.data
                    }
                    is Response.Failure<Exception> -> {
                        mutableThrowables.value = it.error
                    }
                }
            }

        }
    }
}