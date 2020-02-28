package br.com.lsm.androidsample.presentation.core

sealed class State<T> {
    class Success<T>(val data: T) : State<T>()
    class Error<T>(val error: Throwable) : State<T>()
    class Loading<T>(val isLoading: Boolean) : State<T>()
}