package br.com.lsm.androidsample.core

interface BaseView {
    fun showErrorMessage(message: String, action: () -> Unit)
}