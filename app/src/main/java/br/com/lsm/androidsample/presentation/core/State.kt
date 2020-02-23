package br.com.lsm.androidsample.presentation.core

sealed class State<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : State<T>(error = null, data = data)
    class Error<T>(error: Throwable) : State<T>(error = error, data = null)
    class Loading<T>(val isLoading: Boolean) : State<T>(error = null, data = null)
}