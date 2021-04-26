package com.example.themoviechallenge.domain

import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.domain.repository.IGetTvShowRepository
import com.highquality.base.BaseUseCase
import com.highquality.base.Response
import kotlinx.coroutines.flow.Flow

class GetTvShowUseCase(private val repository: IGetTvShowRepository) :
    BaseUseCase<Flow<Response<List<TvShow>>>>() {

    private var language: String? = null
    private var page: Int = 0

    fun bind(
        language: String? = null,
        page: Int = 0
    ) {
        this.language = language
        this.page = page
    }

    override suspend fun execute(): Flow<Response<List<TvShow>>> {
        return repository.getTvShows(this.language, this.page)
    }
}