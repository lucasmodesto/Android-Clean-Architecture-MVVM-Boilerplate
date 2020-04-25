package br.com.lsm.androidsample.core

import br.com.lsm.androidsample.rx.ISchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.TestScheduler

class TestSchedulerProvider(private val testScheduler: TestScheduler) : ISchedulerProvider {

    override fun io(): Scheduler {
        return testScheduler
    }

    override fun computation(): Scheduler {
        return testScheduler
    }

    override fun ui(): Scheduler {
        return testScheduler
    }
}