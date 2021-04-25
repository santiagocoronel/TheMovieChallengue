package com.highquality.base

abstract class BasicUseCase<T> {

    @Throws(Exception::class)
    abstract suspend fun execute(): T

    @Throws(Exception::class)
    open fun execute(dataToValidate: T): T {
        return dataToValidate
    }

}

abstract class BaseUseCase<T> {

    @Throws(Exception::class)
    abstract suspend fun execute(dataToValidate: T): T

}

open class BaseParam()
