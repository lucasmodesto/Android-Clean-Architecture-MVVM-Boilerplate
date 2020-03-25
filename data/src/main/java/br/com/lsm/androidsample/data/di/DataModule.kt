package br.com.lsm.androidsample.data.di

import br.com.lsm.androidsample.data.network.ApolloRxClient
import br.com.lsm.androidsample.data.network.AuthenticationInterceptor
import br.com.lsm.androidsample.data.network.IApolloRxClient
import br.com.lsm.androidsample.data.network.NetworkClientProvider
import br.com.lsm.androidsample.data.repository.GitHubRepository
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DataModule {

    val module = module {

        single<Interceptor>(named(name = Constants.INTERCEPTOR_AUTHENTICATION)) {
            AuthenticationInterceptor(token = get(named(name = Constants.GITHUB_TOKEN)))
        }

        single {
            NetworkClientProvider.providesOkHttpClient(listOf(get(named(name = Constants.INTERCEPTOR_AUTHENTICATION))))
        }

        single {
            NetworkClientProvider.providesApolloClient(
                baseUrl = get(named(name = Constants.BASE_API_URL)),
                client = get()
            )
        }

        single<IApolloRxClient> {
            ApolloRxClient(apolloClient = get())
        }

        single<IGitHubRepository> {
            GitHubRepository(graphQlClient = get())
        }
    }
}