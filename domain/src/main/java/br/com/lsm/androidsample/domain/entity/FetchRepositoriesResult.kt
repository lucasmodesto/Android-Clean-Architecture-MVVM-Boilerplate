package br.com.lsm.androidsample.domain.entity

data class FetchRepositoriesResult(
    val repositories: List<GithubRepo>,
    val paginationData: PaginationData
)