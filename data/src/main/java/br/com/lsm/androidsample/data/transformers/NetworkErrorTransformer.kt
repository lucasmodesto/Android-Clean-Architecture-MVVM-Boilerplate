package br.com.lsm.androidsample.data.transformers

import br.com.lsm.androidsample.data.errors.NetworkError
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.core.SingleTransformer
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorTransformer<T> : SingleTransformer<T, T> {

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.onErrorResumeNext(this::transformNetworkError)
    }

    private fun transformNetworkError(error: Throwable): Single<T> {
        return when (error.cause) {

            is UnknownHostException -> {
                Single.error(NetworkError.NotConnected())
            }

            is SocketTimeoutException -> {
                Single.error(NetworkError.SlowConnection())
            }

            is IOException -> {
                if (error.cause?.message?.contentEquals("Canceled") == true) {
                    Single.error(NetworkError.Canceled())
                } else {
                    Single.error(error)
                }
            }

            else -> {
                Single.error(error)
            }
        }
    }
}