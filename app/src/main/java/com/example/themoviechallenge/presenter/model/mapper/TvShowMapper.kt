package com.example.themoviechallenge.presenter.model.mapper

import com.example.themoviechallenge.data.repository.network.response.TvPageResponse
import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.presenter.model.TvShowModel

object TvShowMapper {

    fun toTvShow(response: TvShow): TvShowModel {
        return response.let {
            TvShowModel(
                backdropPath = it.backdropPath,
                firstAirDate = it.firstAirDate,
                genreIds = it.genreIds,
                id = it.id,
                name = it.name,
                originCountry = it.originCountry,
                originalLanguage = it.originalLanguage,
                originalName = it.originalName,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }!!
    }

}