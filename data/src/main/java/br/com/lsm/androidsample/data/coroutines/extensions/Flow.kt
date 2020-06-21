package br.com.lsm.androidsample.data.coroutines.extensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
fun <T> Flow<T>.retryWhenIsSlowConnection(): Flow<T> {
    return retryWhen { cause, attempt ->
        if (attempt > 1) return@retryWhen false
        when (cause) {
            is SocketTimeoutException, is IOException -> {
                true
            }
            else -> false
        }
    }
}


