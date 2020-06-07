package br.com.lsm.androidsample.core

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseView {

    private val baseActivity by lazy { activity as? BaseActivity }

    override fun showErrorMessage(message: String, action: () -> Unit) {
        baseActivity?.showErrorMessage(message, action)
    }
}