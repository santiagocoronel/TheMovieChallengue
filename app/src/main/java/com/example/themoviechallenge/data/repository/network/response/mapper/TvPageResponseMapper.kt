package com.example.themoviechallenge.data.repository.network.response.mapper

import com.example.themoviechallenge.data.repository.network.response.TvPageResponse
import com.example.themoviechallenge.domain.model.TvShow

object TvPageResponseMapper {

    fun toTvShow(response: TvPageResponse): List<TvShow> {
        return response.results?.map {
            TvShow(
                backdropPath = it.backdropPath!!,
                firstAirDate = it.firstAirDate!!,
                genreIds = it.genreIds!!,
                id = it.id!!,
                name = it.name!!,
                originCountry = it.originCountry!!,
                originalLanguage = it.originalLanguage!!,
                originalName = it.originalName!!,
                overview = it.overview!!,
                popularity = it.popularity!!,
                posterPath = it.posterPath!!,
                voteAverage = it.voteAverage!!,
                voteCount = it.voteCount!!
            )
        }!!
    }

}