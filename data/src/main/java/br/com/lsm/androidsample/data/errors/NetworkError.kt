package br.com.lsm.androidsample.data.errors

sealed class NetworkError : Throwable() {
    class NotConnected : NetworkError()
    class SlowConnection : NetworkError()
}