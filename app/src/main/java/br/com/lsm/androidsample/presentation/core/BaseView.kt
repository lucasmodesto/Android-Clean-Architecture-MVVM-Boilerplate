package br.com.lsm.androidsample.presentation.core

interface BaseView {
    fun showError(message: String, action: () -> Unit)
}