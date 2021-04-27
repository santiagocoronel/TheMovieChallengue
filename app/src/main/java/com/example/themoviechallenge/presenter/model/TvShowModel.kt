package com.example.themoviechallenge.presenter.model

data class TvShowModel(
    val backdropPath: String = "",
    val firstAirDate: String = "",
    val genreIds: List<Int> = listOf(),
    val id: Int = 0,
    val name: String = "",
    val originCountry: List<String> = listOf(),
    val originalLanguage: String = "",
    val originalName: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)