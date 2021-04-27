package com.example.themoviechallenge.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviechallenge.domain.GetRelatedTvShowUseCase
import com.example.themoviechallenge.domain.GetTvShowUseCase
import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.presenter.model.TvShowModel
import com.example.themoviechallenge.presenter.model.mapper.TvShowMapper
import com.highquality.base.BaseViewModel
import com.highquality.base.Response
import com.highquality.base.exception.NoInternetException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlin.Exception

class TvShowViewModel(
    private val getRelatedTvShowUseCase: GetRelatedTvShowUseCase,
    private val getTvShowUseCase: GetTvShowUseCase
) : BaseViewModel() {

    //region page
    private val mutablePageTvShowList: MutableLiveData<Int> = MutableLiveData(1)
    //endregion

    //region tv shows list
    private val mutableTvShowList: MutableLiveData<List<TvShowModel>> = MutableLiveData()
    val tvShowLiveData: LiveData<List<TvShowModel>> get() = mutableTvShowList
    //endregion

    //region page
    private val mutablePageTvShowRelatedList: MutableLiveData<Int> = MutableLiveData(1)
    //endregion

    //region tv shows related list
    private val mutableTvShowRelatedList: MutableLiveData<List<TvShowModel>> = MutableLiveData()
    val tvShowRelatedLiveData: LiveData<List<TvShowModel>> get() = mutableTvShowRelatedList
    //endregion

    fun fetchTvShows(firstTime: Boolean = false) {
        viewModelScope.launch {
            if (firstTime) notifyShowLoading()
            getTvShowUseCase.bind(language = null, page = mutablePageTvShowList.value!!)
            executeSimpleUseCase(getTvShowUseCase).single().collect {
                notifyRemoveLoading()
                when (it) {
                    is Response.Success<List<TvShow>> -> {
                        mutableTvShowList.value =
                            it.data.map { item -> TvShowMapper.toTvShow(item) }
                        mutablePageTvShowList.value = mutablePageTvShowList.value!! + 1
                    }
                    is Response.Failure<Exception> -> {
                        when (it.error) {
                            is NoInternetException -> {
                                mutableConnection.value = false
                            }
                            else -> {
                                notifyError(it.error)
                            }
                        }
                    }
                }
            }

        }
    }

    fun fetchTvShowsRelated(tvId: String) {
        viewModelScope.launch {
            getRelatedTvShowUseCase.bind(tvId = tvId, language = null, page = 1)
            executeSimpleUseCase(getRelatedTvShowUseCase).single().collect {
                when (it) {
                    is Response.Success<List<TvShow>> -> {
                        mutableTvShowRelatedList.value =
                            it.data.map { item -> TvShowMapper.toTvShow(item) }
                    }
                    is Response.Failure<Exception> -> {
                        when (it.error) {
                            is NoInternetException -> {
                                mutableConnection.value = false
                            }
                            else -> {
                                notifyError(it.error)
                            }
                        }
                    }
                }
            }

        }
    }

    fun resetPaginationPageTvShowList() {
        mutablePageTvShowList.value = 1
    }

    fun resetPaginationPageTvShowRelatedList() {
        mutablePageTvShowRelatedList.value = 1
    }
}