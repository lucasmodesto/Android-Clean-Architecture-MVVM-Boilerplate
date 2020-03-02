package br.com.lsm.androidsample.data.rx

import br.com.lsm.androidsample.data.errors.NetworkError
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorTransformer<T> : SingleTransformer<T, T> {

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.onErrorResumeNext(this::transformNetworkError)
    }

    private fun transformNetworkError(error: Throwable): Single<T> {
        return when (error) {

            is UnknownHostException -> {
                Single.error<T>(NetworkError.NotConnected())
            }

            is SocketTimeoutException -> {
                Single.error<T>(NetworkError.SlowConnection())
            }

            is IOException -> {
                if (error.message?.contentEquals("Canceled") == true) {
                    Single.error<T>(NetworkError.Canceled())
                } else {
                    Single.error<T>(error)
                }
            }

            else -> {
                Single.error<T>(error)
            }
        }
    }


}