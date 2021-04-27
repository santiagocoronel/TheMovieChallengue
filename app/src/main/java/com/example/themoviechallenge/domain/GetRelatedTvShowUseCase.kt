package com.example.themoviechallenge.domain

import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.domain.repository.IGetRelatedTvShowRepository
import com.example.themoviechallenge.base.domain.BaseUseCase
import com.example.themoviechallenge.base.data.Response
import kotlinx.coroutines.flow.Flow

class GetRelatedTvShowUseCase(private val repository: IGetRelatedTvShowRepository) :
    BaseUseCase<Flow<Response<List<TvShow>>>>() {

    private lateinit var tvId: String
    private var language: String? = null
    private var page: Int = 0

    fun bind(
        tvId: String,
        language: String? = null,
        page: Int = 0
    ) {
        this.tvId = tvId
        this.language = language
        this.page = page
    }


    override suspend fun execute(): Flow<Response<List<TvShow>>> {
        return repository.getRelatedTvShows(this.tvId, this.language, this.page)
    }

}