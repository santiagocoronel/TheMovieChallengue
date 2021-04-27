package com.example.themoviechallenge.base.domain

abstract class BaseUseCase<T> {

    @Throws(Exception::class)
    abstract suspend fun execute(): T

}