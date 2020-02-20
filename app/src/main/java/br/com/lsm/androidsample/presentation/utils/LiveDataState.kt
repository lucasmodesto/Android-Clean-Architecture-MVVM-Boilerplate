package br.com.lsm.androidsample.presentation.utils

sealed class LiveDataState<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : LiveDataState<T>(data = data)
    class Error<T>(error: Throwable) : LiveDataState<T>(error = error)
    class Loading<T> : LiveDataState<T>()
}