package br.com.lsm.androidsample.data.coroutines

import kotlinx.coroutines.Dispatchers

class DispatcherProvider: IDispatcherProvider {

    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Default
}