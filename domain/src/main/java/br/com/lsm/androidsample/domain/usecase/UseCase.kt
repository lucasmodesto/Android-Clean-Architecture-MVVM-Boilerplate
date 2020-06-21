package br.com.lsm.androidsample.domain.usecase

import kotlinx.coroutines.flow.Flow


abstract class UseCase {

    object FromFlow {
        interface WithInput<in Input, out Output> {
            fun execute(params: Input): Flow<Output>
        }

        interface WithoutInput<out Output> {
            fun execute(): Flow<Output>
        }
    }
}