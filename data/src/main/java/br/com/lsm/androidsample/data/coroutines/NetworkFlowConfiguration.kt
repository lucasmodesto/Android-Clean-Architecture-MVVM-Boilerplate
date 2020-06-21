package br.com.lsm.androidsample.data.coroutines

import br.com.lsm.androidsample.data.coroutines.extensions.retryWhenIsSlowConnection
import kotlinx.coroutines.flow.Flow

class NetworkFlowConfiguration : IFlowConfiguration {

    override fun <T> apply(flow: Flow<T>): Flow<T> {
        return flow.retryWhenIsSlowConnection()
    }
}