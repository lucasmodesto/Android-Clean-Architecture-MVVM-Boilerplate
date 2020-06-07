package br.com.lsm.androidsample.core

import androidx.lifecycle.Observer
import io.mockk.spyk

object TestUtils {

    fun <T> createStateSpyObserver(): Observer<State<T>> =
        spyk(Observer { })
}