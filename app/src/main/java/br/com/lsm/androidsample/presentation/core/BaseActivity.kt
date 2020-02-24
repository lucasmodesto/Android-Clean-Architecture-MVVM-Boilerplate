package br.com.lsm.androidsample.presentation.core

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.data.errors.NetworkError
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), BaseView {

    protected val viewModel: VM by lazy { getViewModel(getViewModelClass()) }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): KClass<VM> {
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>).kotlin
    }

    override fun showError(message: String, action: () -> Unit) {
        if (!isFinishing) {
            Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_INDEFINITE
            ).setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                .setAction(getString(R.string.message_retry)) {
                    action.invoke()
                }.show()
        }
    }

    protected fun handleError(error: Throwable, retryAction: () -> Unit) {
        when (error) {

            is NetworkError.NotConnected -> {
                showError(getString(R.string.message_no_internet), retryAction)
            }

            is NetworkError.SlowConnection -> {
                showError(getString(R.string.message_slow_internet), retryAction)
            }

            else -> {
                showError(getString(R.string.message_unknown_error), retryAction)
            }
        }
    }
}