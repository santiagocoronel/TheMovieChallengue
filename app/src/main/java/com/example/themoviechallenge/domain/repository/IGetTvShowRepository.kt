package com.example.themoviechallenge.domain.repository

import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.base.data.Response
import kotlinx.coroutines.flow.Flow

interface IGetTvShowRepository {

    suspend fun getTvShows(
        language: String? = null,
        page: Int = 0
    ): Flow<Response<List<TvShow>>>

}