package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.GithubRepository

interface IGetRepositoriesUseCase :
    UseCase.FromSingle.WithInput<GetRepositoriesInput, List<GithubRepository>>