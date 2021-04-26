package com.example.themoviechallenge.data.repository.network.response


import com.google.gson.annotations.SerializedName

data class TheMovieErrorResponse(
    @SerializedName("status_code")
    val statusCode: Int? = 0,
    @SerializedName("status_message")
    val statusMessage: String? = "",
    @SerializedName("success")
    val success: Boolean? = false
)