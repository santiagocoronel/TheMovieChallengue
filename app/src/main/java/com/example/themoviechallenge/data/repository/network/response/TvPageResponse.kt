package com.example.themoviechallenge.data.repository.network.response


import com.google.gson.annotations.SerializedName

data class TvPageResponse(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<TvResponse>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)