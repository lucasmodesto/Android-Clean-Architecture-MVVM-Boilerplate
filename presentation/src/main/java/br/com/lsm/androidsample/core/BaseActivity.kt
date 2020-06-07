package br.com.lsm.androidsample.core

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.lsm.androidsample.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(), BaseView {

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
}