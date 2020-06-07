package br.com.lsm.androidsample.extensions

import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.core.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
suspend fun <T> MutableLiveData<State<T>>.applyStateFrom(flow: Flow<T>, onCollected: (T) -> Unit) {
    flow.onStart {
        value = State.Loading(isLoading = true)
    }.onCompletion {
        value = State.Loading(isLoading = false)
    }.catch {
        value = State.Error(error = it)
    }.collect {
        onCollected(it)
        value = State.Success(data = it)
    }
}