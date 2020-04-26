package br.com.lsm.androidsample.core

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    @VisibleForTesting
    val disposables = CompositeDisposable()

    override fun onCleared() {
        if (!disposables.isDisposed) disposables.clear()
        super.onCleared()
    }

}