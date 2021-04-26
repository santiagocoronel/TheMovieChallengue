package com.example.themoviechallenge.domain.repository

import com.example.themoviechallenge.domain.model.TvShow
import com.highquality.base.Response
import kotlinx.coroutines.flow.Flow

interface IGetTvShowRepository {

    suspend fun getTvShows(
        language: String? = null,
        page: Int = 0
    ): Flow<Response<List<TvShow>>>

}