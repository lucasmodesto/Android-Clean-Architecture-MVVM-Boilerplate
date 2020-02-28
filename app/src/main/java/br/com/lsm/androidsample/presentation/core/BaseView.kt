package br.com.lsm.androidsample.presentation.core

interface BaseView {
    fun showErrorMessage(message: String, action: () -> Unit)
    fun showLoading()
    fun hideLoading()
}