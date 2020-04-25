package br.com.lsm.androidsample.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.lsm.androidsample.data.di.DataModule
import br.com.lsm.androidsample.domain.di.DomainModule
import br.com.lsm.androidsample.di.PresentationModule
import br.com.lsm.androidsample.rx.ISchedulerProvider
import br.com.lsm.androidsample.rx.TrampolineSchedulersProvider
import org.junit.Rule
import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class BaseTest : KoinTest {

    abstract val moduleConfig: (KoinApplication) -> Module

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val koinRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(
            listOf(
                PresentationModule.configModule,
                PresentationModule.viewModelsModule,
                DomainModule.module,
                DataModule.module
            )
        )
        modules(module {
            single<ISchedulerProvider>(override = true) {
                TrampolineSchedulersProvider()
            }
        })
        modules(moduleConfig.invoke(this))
    }
}