package br.com.lsm.androidsample.di

import br.com.lsm.androidsample.BuildConfig
import br.com.lsm.androidsample.data.di.Constants
import br.com.lsm.androidsample.rx.DefaultSchedulerProvider
import br.com.lsm.androidsample.rx.ISchedulerProvider
import br.com.lsm.androidsample.search.SearchRepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object PresentationModule {

    val viewModelsModule = module {
        viewModel {
            SearchRepositoriesViewModel(
                getRepositoriesUseCase = get(),
                schedulerProvider = get()
            )
        }
    }

    val configModule = module {
        single(named(name = Constants.BASE_API_URL)) { BuildConfig.GITHUB_API_URL }
        single(named(name = Constants.GITHUB_TOKEN)) { BuildConfig.GITHUB_API_TOKEN }
        single<ISchedulerProvider> { DefaultSchedulerProvider() }
    }
}