package br.com.lsm.androidsample.domain.usecase

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

abstract class UseCase {

    object FromObservable {

        interface WithInput<in Input, Result> {
            fun execute(params: Input): Observable<Result>
        }

        interface WithoutInput<Result> {
            fun execute(): Observable<Result>
        }
    }

    object FromSingle {

        interface WithInput<in Input, Result> {
            fun execute(params: Input): Single<Result>
        }

        interface WithoutInput<Result> {
            fun execute(): Single<Result>
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
}