package br.com.lsm.androidsample.data.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherProvider {

    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}