package br.com.lsm.androidsample.data.extensions

import br.com.lsm.androidsample.data.transformers.NetworkErrorTransformer
import io.reactivex.rxjava3.core.Single

fun <T> Single<T>.composeErrorTransformers(): Single<T> {
    return this.compose(NetworkErrorTransformer())
}