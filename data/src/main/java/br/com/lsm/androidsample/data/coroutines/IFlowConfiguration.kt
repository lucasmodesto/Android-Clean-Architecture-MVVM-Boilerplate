package br.com.lsm.androidsample.data.coroutines

import kotlinx.coroutines.flow.Flow

interface IFlowConfiguration {
    fun <T> apply(flow: Flow<T>): Flow<T>
}