package br.com.lsm.androidsample.data.repository

import FetchRepositoriesQuery
import br.com.lsm.androidsample.data.mapper.FetchRepositoriesMapper
import br.com.lsm.androidsample.data.model.request.LanguageQuery
import br.com.lsm.androidsample.data.network.IApolloClient
import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.entity.PaginationData
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import com.apollographql.apollo.api.Input
import kotlinx.coroutines.flow.flow

class GitHubRepository(private val graphQlClient: IApolloClient) : IGitHubRepository {

    override fun getRepositories(
        language: Language,
        paginationCursor: String?
    ) = flow {
        val queryResult = graphQlClient.query(
            FetchRepositoriesQuery(
                query = String.format(
                    "language:%s",
                    LanguageQuery.getValue(language)
                ), cursor = Input.fromNullable(paginationCursor)
            )
        )
        val repositories = FetchRepositoriesResult(
            repositories = queryResult.search.nodes?.mapNotNull { nodes -> nodes?.asRepository }
                ?.map { asRepository -> FetchRepositoriesMapper.map(asRepository) }
                ?: emptyList()
            , paginationData = PaginationData(
                hasNextPage = queryResult.search.pageInfo.hasNextPage,
                endCursor = queryResult.search.pageInfo.endCursor
            )
        )
        emit(repositories)
    }
}