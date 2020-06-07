package br.com.lsm.androidsample.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException

class ApolloClient(private val apolloClient: ApolloClient) : IApolloClient {

    override suspend fun <D : Operation.Data, T, V : Operation.Variables> query(query: Query<D, T, V>): T {
        try {
            val result = apolloClient.query(query).toDeferred().await()
            if (result.hasErrors()) {
                val errors = result.errors()
                    .filterNot { it.message().isNullOrEmpty() }
                    .map { it.message().orEmpty() }
                throw ApolloException(errors.toString())
            } else {
                result.data()?.let {
                    return it
                } ?: throw ApolloException("Empty data")
            }

        } catch (ex: ApolloException) {
            throw(ex.cause ?: ex)
        }
    }
}