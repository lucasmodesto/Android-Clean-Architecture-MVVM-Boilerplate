package br.com.lsm.androidsample.presentation.core

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), BaseView {

    protected val viewModel: VM by lazy { getViewModel(getViewModelClass()) }

    private val baseActivity by lazy { activity as? BaseActivity<*> }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): KClass<VM> {
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>).kotlin
    }

    override fun showErrorMessage(message: String, action: () -> Unit) {
        baseActivity?.showErrorMessage(message, action)
    }
}