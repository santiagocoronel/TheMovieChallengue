package com.example.themoviechallenge.domain.repository

import com.example.themoviechallenge.domain.model.TvShow
import com.highquality.base.Response
import kotlinx.coroutines.flow.Flow

interface IGetRelatedTvShowRepository {

    suspend fun getRelatedTvShows(
        tvId: String,
        language: String? = null,
        page: Int = 0
    ): Flow<Response<List<TvShow>>>

}