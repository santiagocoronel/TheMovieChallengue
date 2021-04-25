package com.highquality.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel : ViewModel() {

    val mutableConnection = MutableLiveData<Boolean>()
    var mutableLoading = MutableLiveData<Boolean>()
    var mutableThrowables = MutableLiveData<Throwable?>()

    fun <T> executeSimpleUseCase(useCase: BasicUseCase<T>): Flow<T> = flow {
        executeUseCase(useCase, this)
    }.flowOn(Dispatchers.Main)
        .catch { it.printStackTrace() }
        .onEach { }

    private suspend fun <T> executeUseCase(
        useCase: BasicUseCase<T>,
        flowCollector: FlowCollector<T>
    ) {
        flowCollector.emit(useCase.execute())
    }


    fun notifyShowLoading() {
        mutableLoading.value = true
    }

    fun notifyRemoveLoading() {
        mutableLoading.value = false
    }

    private fun notifyNoConnection() {
        mutableConnection.postValue(false)
    }

    fun notifyError(exception: Exception) {
        mutableThrowables.value = exception
    }

}