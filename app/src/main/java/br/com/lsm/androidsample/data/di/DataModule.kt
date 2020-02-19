package br.com.lsm.androidsample.data.di

import br.com.lsm.androidsample.BuildConfig
import br.com.lsm.androidsample.data.network.GitHubService
import br.com.lsm.androidsample.data.network.NetworkClientProvider
import br.com.lsm.androidsample.data.repository.GitHubRepository
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import org.koin.dsl.module

object DataModule {

    val module = module {
        single {
            NetworkClientProvider.providesRetrofitService(
                baseUrl = BuildConfig.GITHUB_API_URL,
                service = GitHubService::class.java
            )
        }

        single<IGitHubRepository> {
            GitHubRepository(service = get())
        }
    }
}