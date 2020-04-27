package br.com.lsm.androidsample.extensions

import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.core.State
import br.com.lsm.androidsample.rx.ISchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

fun <T> Single<T>.subscribeWithLiveDataState(liveData: MutableLiveData<State<T>>): Disposable {
    return this.doOnSubscribe { liveData.value = State.Loading(isLoading = true) }
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

fun <T> Single<T>.applyDefaultSchedulers(schedulerProvider: ISchedulerProvider): Single<T> {
    return this
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}

fun <T> Observable<T>.applyDefaultSchedulers(schedulerProvider: ISchedulerProvider): Observable<T> {
    return this
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}

