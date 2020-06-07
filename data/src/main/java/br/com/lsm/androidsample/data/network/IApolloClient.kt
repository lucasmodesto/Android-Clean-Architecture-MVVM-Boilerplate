package br.com.lsm.androidsample.data.network

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query

interface IApolloClient {
    suspend fun <D : Operation.Data, T, V : Operation.Variables> query(query: Query<D, T, V>): T
}