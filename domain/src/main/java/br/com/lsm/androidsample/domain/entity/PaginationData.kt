package br.com.lsm.androidsample.domain.entity

data class PaginationData(
    val hasNextPage: Boolean,
    val endCursor: String?
)