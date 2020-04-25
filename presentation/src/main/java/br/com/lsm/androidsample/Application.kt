package br.com.lsm.androidsample

import android.app.Application
import br.com.lsm.androidsample.data.di.DataModule
import br.com.lsm.androidsample.domain.di.DomainModule
import br.com.lsm.androidsample.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            if (BuildConfig.DEBUG) androidLogger()

            modules(
                listOf(
                    DataModule.module,
                    DomainModule.module,
                    PresentationModule.viewModelsModule,
                    PresentationModule.configModule
                )
            )
        }
    }
}