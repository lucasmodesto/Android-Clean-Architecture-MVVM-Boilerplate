package br.com.lsm.androidsample.data.di

import br.com.lsm.androidsample.data.network.GitHubService
import br.com.lsm.androidsample.data.network.NetworkClientProvider
import br.com.lsm.androidsample.data.repository.GitHubRepository
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DataModule {

    val module = module {

        single {
            NetworkClientProvider.providesOkHttpClient()
        }

        single {
            NetworkClientProvider.providesRetrofitService(
                baseUrl = get(named(name = Constants.BASE_API_URL)),
                service = GitHubService::class.java,
                client = get()
            )
        }

        single<IGitHubRepository> {
           GitHubRepository(service = get())
        }
    }
}