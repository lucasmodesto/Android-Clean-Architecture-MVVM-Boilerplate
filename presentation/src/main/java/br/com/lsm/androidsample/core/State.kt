package br.com.lsm.androidsample.core

import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.data.errors.NetworkError

sealed class State<T> {
    class Success<T>(val data: T) : State<T>()
    class Error<T>(val error: Throwable) : State<T>() {

        fun getErrorMessage(): Int {
            return when (error) {
                is NetworkError.NotConnected -> R.string.message_no_internet
                is NetworkError.SlowConnection -> R.string.message_slow_internet
                else -> R.string.message_unknown_error
            }
        }
    }
    class Loading<T>(val isLoading: Boolean) : State<T>()
}