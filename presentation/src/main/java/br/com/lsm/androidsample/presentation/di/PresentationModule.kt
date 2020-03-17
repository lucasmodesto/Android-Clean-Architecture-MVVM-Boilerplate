package br.com.lsm.androidsample.presentation.di

import br.com.lsm.androidsample.BuildConfig
import br.com.lsm.androidsample.data.di.Constants
import br.com.lsm.androidsample.presentation.githubList.RepositoriesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object PresentationModule {

    val viewModelsModule = module {
        viewModel {
            RepositoriesListViewModel(
                getRepositoriesUseCase = get()
            )
        }
    }

    val configModule = module {
        single(named(name = Constants.BASE_API_URL)) { BuildConfig.GITHUB_API_URL }
    }
}