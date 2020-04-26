package br.com.lsm.androidsample.search

import br.com.lsm.androidsample.core.BaseTest
import br.com.lsm.androidsample.core.TestUtils
import br.com.lsm.androidsample.domain.entity.*
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.core.State
import com.google.common.truth.Truth
import io.mockk.*
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject

class SearchRepositoriesViewModelTest : BaseTest() {

    private val viewmodel: SearchRepositoriesViewModel by inject()
    private val getRepositoriesUseCaseMock: IGetRepositoriesUseCase by inject()

    override val moduleConfig: (KoinApplication) -> Module = {
        module {
            single<IGetRepositoriesUseCase>(override = true) { mockk() }
        }
    }

    @Test
    fun `test fetchRepositories success behavior`() {
        val observerMock = TestUtils.createMockedObserver<State<FetchRepositoriesResult>>()
        viewmodel.getRepositories().observeForever(observerMock)

        val data = mockk<FetchRepositoriesResult>(relaxed = true)

        every { getRepositoriesUseCaseMock.execute(any()) } returns Single.just(data)
        viewmodel.fetchRepositories()

        verify {
            getRepositoriesUseCaseMock.execute(any())
        }

        val slots = mutableListOf<State<FetchRepositoriesResult>>()
        verify { observerMock.onChanged(capture(slots)) }

        val showLoadingState = slots[0] as State.Loading
        val hideLoadingState = slots[1] as State.Loading
        val successState = slots[2] as State.Success

        Truth.assertThat(showLoadingState.isLoading)
        Truth.assertThat(!hideLoadingState.isLoading)
        Truth.assertThat(successState.data).isEqualTo(data)
    }
}