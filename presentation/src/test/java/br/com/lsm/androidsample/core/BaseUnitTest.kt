package br.com.lsm.androidsample.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.lsm.androidsample.data.di.DataModule
import br.com.lsm.androidsample.di.PresentationModule
import br.com.lsm.androidsample.domain.di.DomainModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

open class BaseUnitTest : KoinTest {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

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
    }

    @ExperimentalCoroutinesApi
    @Before
    fun runBefore() {
        Dispatchers.setMain(dispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun runAfter() {
        stopKoin()
        Dispatchers.resetMain()
    }

    protected fun loadKoin(block: Module.() -> Unit) {
        loadKoinModules(module {
            block.invoke(this)
        })
    }
}