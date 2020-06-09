package br.com.lsm.androidsample.core

import br.com.lsm.androidsample.data.errors.NetworkError
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHelper {

    fun transformNetworkErrorOrKeep(error: Throwable): Throwable {
        return when (error) {
            is UnknownHostException -> NetworkError.NotConnected()
            is SocketTimeoutException, is IOException-> NetworkError.SlowConnection()
            else -> error
        }
    }
}