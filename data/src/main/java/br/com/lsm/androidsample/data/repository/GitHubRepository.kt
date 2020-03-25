package br.com.lsm.androidsample.data.repository

import FetchRepositoriesQuery
import br.com.lsm.androidsample.data.mapper.FetchRepositoriesMapper
import br.com.lsm.androidsample.data.model.request.LanguageQuery
import br.com.lsm.androidsample.data.network.IApolloRxClient
import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.entity.PaginationData
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import com.apollographql.apollo.api.Input
import io.reactivex.rxjava3.core.Single

class GitHubRepository(private val graphQlClient: IApolloRxClient) : IGitHubRepository {

    override fun getRepositories(
        language: Language,
        paginationCursor: String?
    ): Single<FetchRepositoriesResult> {
        return graphQlClient.query(
            FetchRepositoriesQuery(
                query = String.format(
                    "language:%s",
                    LanguageQuery.getValue(language)
                ), cursor = Input.fromNullable(paginationCursor)
            )
        ).map {
            FetchRepositoriesResult(
                repositories = it.search.nodes?.mapNotNull { nodes -> nodes?.asRepository }
                    ?.map { asRepository -> FetchRepositoriesMapper.map(asRepository) }
                    ?: emptyList()
                , paginationData = PaginationData(
                    hasNextPage = it.search.pageInfo.hasNextPage,
                    endCursor = it.search.pageInfo.endCursor
                )
            )
        }
    }
}