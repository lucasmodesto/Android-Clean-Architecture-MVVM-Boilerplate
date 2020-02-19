package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.GithubRepository

interface IGetRepositoriesUseCase :
    UseCase.FromObservable.WithInput<GetRepositoriesInput, List<GithubRepository>>