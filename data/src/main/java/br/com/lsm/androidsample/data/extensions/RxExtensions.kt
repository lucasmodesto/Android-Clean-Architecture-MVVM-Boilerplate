package br.com.lsm.androidsample.data.extensions

import br.com.lsm.androidsample.data.rx.NetworkErrorTransformer
import io.reactivex.Single

fun <T> Single<T>.composeErrorTransformers(): Single<T> {
    return this.compose(NetworkErrorTransformer())
}