package br.com.lsm.androidsample.presentation.di

import br.com.lsm.androidsample.presentation.RepositoriesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {

    val viewModelsModule = module {
        viewModel { RepositoriesListViewModel(getRepositoriesUseCase = get()) }
    }
}