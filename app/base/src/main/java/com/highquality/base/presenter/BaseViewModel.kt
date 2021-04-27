package com.highquality.base.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.highquality.base.domain.BaseUseCase
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

    fun <T> executeSimpleUseCase(useCase: BaseUseCase<T>): Flow<T> = flow {
        notifyConnection()
        executeUseCase(useCase, this)
    }.flowOn(Dispatchers.Main)
        .catch { it.printStackTrace() }
        .onEach { }

    private suspend fun <T> executeUseCase(
        useCase: BaseUseCase<T>,
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

    private fun notifyConnection() {
        mutableConnection.postValue(true)
    }

    private fun notifyNoConnection() {
        mutableConnection.postValue(false)
    }

    fun notifyError(exception: Exception) {
        mutableThrowables.value = exception
    }

}