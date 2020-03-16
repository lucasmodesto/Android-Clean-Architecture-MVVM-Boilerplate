package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.Language

data class GetRepositoriesInput(
    var language: Language,
    var page: Int
)