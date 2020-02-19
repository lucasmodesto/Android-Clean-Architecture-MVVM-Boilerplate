package br.com.lsm.androidsample.presentation.extensions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.applyDefaultSchedulers(): Observable<T> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}