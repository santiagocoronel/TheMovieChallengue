package com.highquality.base

abstract class BaseUseCase<T> {

    @Throws(Exception::class)
    abstract suspend fun execute(): T

}