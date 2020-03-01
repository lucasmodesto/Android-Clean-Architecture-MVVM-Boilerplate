package br.com.lsm.androidsample.domain.usecase

data class GetRepositoriesInput(
    var language: String,
    var page: Int
)