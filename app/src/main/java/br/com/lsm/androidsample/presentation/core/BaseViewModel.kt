package br.com.lsm.androidsample.presentation.core

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        if (!disposables.isDisposed) disposables.clear()
        super.onCleared()
    }
}