package br.com.lsm.androidsample.data.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.data.rx.NetworkErrorTransformer
import br.com.lsm.androidsample.presentation.core.State
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.defaultSchedulers(): Observable<T> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.defaultSchedulers(): Single<T> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.composeErrorTransformers(): Single<T> {
    return this.compose(NetworkErrorTransformer())
}