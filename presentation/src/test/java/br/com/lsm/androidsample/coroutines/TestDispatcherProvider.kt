package br.com.lsm.androidsample.coroutines

import br.com.lsm.androidsample.data.coroutines.IDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : IDispatcherProvider {

    override fun io() = TestCoroutineDispatcher()

    override fun main() = TestCoroutineDispatcher()
}