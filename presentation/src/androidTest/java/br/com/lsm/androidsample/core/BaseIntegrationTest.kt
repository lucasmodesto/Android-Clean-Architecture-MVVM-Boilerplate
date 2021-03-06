package br.com.lsm.androidsample.core

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class BaseIntegrationTest: KoinTest {

    private val idlingResource = OkHttp3IdlingResource.create("okhttp", get())

    protected fun loadKoin(block: Module.() -> Unit) {
        loadKoinModules(module { block.invoke(this) })
    }

    @Before
    fun before() {
        startKoin {

        }
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}