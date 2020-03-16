package br.com.lsm.androidsample.domain.usecase

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

abstract class UseCase {

    object FromObservable {

        interface WithInput<in Input, Output> {
            fun execute(params: Input): Observable<Output>
        }

        interface WithoutInput<Output> {
            fun execute(): Observable<Output>
        }
    }

    object FromSingle {

        interface WithInput<in Input, Output> {
            fun execute(params: Input): Single<Output>
        }

        interface WithoutInput<Output> {
            fun execute(): Single<Output>
        }
    }

    object FromCompletable {

        interface WithInput<in Input> {
            fun execute(params: Input): Completable
        }

        interface WithoutInput {
            fun execute(): Completable
        }
    }

    object FromCallback {

        interface WithInput<in Input, Output> {
            fun execute(params: Input, onSuccess: (Output) -> Unit, onError: (Throwable) -> Unit)
        }

        interface WithoutInput<Output> {
            fun execute(onSuccess: (Output) -> Unit, onError: (Throwable) -> Unit)
        }
    }
}