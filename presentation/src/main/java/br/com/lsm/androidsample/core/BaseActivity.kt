package br.com.lsm.androidsample.core

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.lsm.androidsample.R
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), BaseView {

    protected val viewModel: VM by lazy { getViewModel(getViewModelClass()) }

    override fun showErrorMessage(message: String, action: () -> Unit) {
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

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): KClass<VM> {
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>).kotlin
    }
}