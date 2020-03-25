package br.com.lsm.androidsample.data.network

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.Single

class ApolloRxClient(private val apolloClient: ApolloClient) : IApolloRxClient {

    override fun <D : Operation.Data, T, V : Operation.Variables> query(query: Query<D, T, V>): Single<T> {
        return Single.create { emitter ->
            enqueue(query = query,
                onSuccess = {
                    it?.run {
                        if (!emitter.isDisposed) {
                            emitter.onSuccess(this)
                        }
                    } ?: run {
                        if (!emitter.isDisposed) {
                            emitter.tryOnError(ApolloException("Empty data"))
                        }
                    }
                },
                onError = {
                    if (!emitter.isDisposed) {
                        emitter.tryOnError(it)
                    }
                })
        }
    }

    private fun <D : Operation.Data, T, V : Operation.Variables> enqueue(
        query: Query<D, T, V>,
        onSuccess: (T?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            apolloClient.query(query).enqueue(object : ApolloCall.Callback<T>() {

                override fun onFailure(e: ApolloException) {
                    onError.invoke(e)
                }

                override fun onResponse(response: Response<T>) {
                    if (response.hasErrors()) {
                        val errors = response.errors()
                            .filterNot { it.message().isNullOrEmpty() }
                            .map { it.message().orEmpty() }
                        onError.invoke(ApolloException(errors.toString()))
                    } else {
                        val data = response.data()
                        onSuccess.invoke(data)
                    }
                }
            })
        } catch (e: Exception) {
            onError.invoke(e)
        }
    }
}