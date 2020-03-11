package br.com.lsm.androidsample.presentation.core

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()


    protected fun handleError(error: Throwable) {

    }

    override fun onCleared() {
        if (!disposables.isDisposed) disposables.clear()
        super.onCleared()
    }
}