package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult

interface IGetRepositoriesUseCase :
    UseCase.FromFlow.WithInput<GetRepositoriesInput,FetchRepositoriesResult>