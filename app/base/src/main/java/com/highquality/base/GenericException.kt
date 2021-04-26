package com.highquality.base

data class GenericException(
    val statusCode: Int,
    val statusMessage: String
) : Exception() {


}