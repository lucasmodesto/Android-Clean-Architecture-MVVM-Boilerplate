package br.com.lsm.androidsample.core

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        if (!disposables.isDisposed) disposables.clear()
        super.onCleared()
    }
}