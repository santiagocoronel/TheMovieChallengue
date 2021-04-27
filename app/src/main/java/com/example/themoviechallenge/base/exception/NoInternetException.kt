package com.example.themoviechallenge.base.exception

data class NoInternetException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()