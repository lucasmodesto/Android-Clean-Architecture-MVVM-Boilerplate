package br.com.lsm.androidsample.presentation.extensions

import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.presentation.core.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Single<T>.subscribeWithLiveDataState(liveData: MutableLiveData<State<T>>): Disposable {
    return this.doOnSubscribe {
            liveData.value = State.Loading(isLoading = true)
        }
        .doOnSuccess {
            liveData.value = State.Loading(isLoading = false)
            liveData.value = State.Success(data = it)
        }.doOnError {
            liveData.value = State.Loading(isLoading = false)
            liveData.value = State.Error(error = it)
        }.subscribe { _, _ -> }
}

fun <T> Observable<T>.subscribeWithLiveDataState(liveData: MutableLiveData<State<T>>): Disposable {
    return this.doOnSubscribe { liveData.value = State.Loading(isLoading = true) }
        .doOnTerminate { liveData.value = State.Loading(isLoading = false) }
        .doOnNext { liveData.value = State.Success(data = it) }
        .doOnError { liveData.value = State.Error(error = it) }
        .subscribe({}, {})
}

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
