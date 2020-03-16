package br.com.lsm.androidsample.presentation.di

import br.com.lsm.androidsample.domain.usecase.GetRepositoriesUseCase
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import org.koin.dsl.module

object DomainModule {

    val module = module {

        single<IGetRepositoriesUseCase> {
            GetRepositoriesUseCase(repository = get())
        }
    }

}