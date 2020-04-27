package br.com.lsm.androidsample.core

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.jakewharton.espresso.OkHttp3IdlingResource
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

open class BaseAndroidTest : KoinTest {

    private lateinit var idlingResource: IdlingResource

    protected fun setupKoin(block: Module.() -> Unit) {
        loadKoinModules(module { block.invoke(this) })
    }

    @Before
    fun before() {
        idlingResource = OkHttp3IdlingResource.create("okhttp", get())
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}