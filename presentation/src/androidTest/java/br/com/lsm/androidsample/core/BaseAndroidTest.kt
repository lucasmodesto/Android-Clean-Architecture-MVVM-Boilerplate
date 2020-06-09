package br.com.lsm.androidsample.core

import org.junit.After
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest

open class BaseAndroidTest : KoinTest {

    @After
    fun cleanUp() {
        stopKoin()
    }

    protected fun loadKoin(block: Module.() -> Unit) {
        loadKoinModules(module { block.invoke(this) })
    }

}