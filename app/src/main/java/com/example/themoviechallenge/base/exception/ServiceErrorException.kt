package com.example.themoviechallenge.base.exception

data class ServiceErrorException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()