package br.com.lsm.androidsample.domain.usecase

data class GetRepositoriesInput(
    val language: String,
    val sort: String,
    var page: Int
)