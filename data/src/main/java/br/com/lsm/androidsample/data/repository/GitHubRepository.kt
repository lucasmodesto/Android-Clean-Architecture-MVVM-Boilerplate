package br.com.lsm.androidsample.data.repository

import FetchRepositoriesQuery
import br.com.lsm.androidsample.data.mapper.RepositoryMapper
import br.com.lsm.androidsample.data.model.request.LanguageQuery
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.Single
import java.lang.RuntimeException

class GitHubRepository(
    private val apolloClient: ApolloClient
) : IGitHubRepository {

    override fun getRepositories(
        language: Language,
        page: Int
    ): Single<List<GithubRepo>> {
        apolloClient.query(
            FetchRepositoriesQuery(
                query = String.format(
                    "language:%s",
                    LanguageQuery.getValue(language)
                )
            )
        ).enqueue(object: ApolloCall.Callback<FetchRepositoriesQuery.Data>(){

            override fun onFailure(e: ApolloException) {
              print("onFailure")
            }

            override fun onResponse(response: Response<FetchRepositoriesQuery.Data>) {
                print("onResponse")
            }

        })
        return Single.error(RuntimeException())
    }

}