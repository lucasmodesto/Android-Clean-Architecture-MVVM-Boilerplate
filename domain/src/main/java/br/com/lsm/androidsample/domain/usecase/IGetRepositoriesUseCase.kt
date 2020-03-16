package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.GithubRepo

interface IGetRepositoriesUseCase :
    UseCase.FromSingle.WithInput<GetRepositoriesInput, List<GithubRepo>>