package br.com.lsm.androidsample.search

import br.com.lsm.androidsample.core.BaseUnitTest
import br.com.lsm.androidsample.core.State
import br.com.lsm.androidsample.core.TestUtils
import br.com.lsm.androidsample.data.errors.NetworkError
import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.entity.PaginationData
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import com.google.common.truth.Truth
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import java.net.UnknownHostException

class SearchRepositoriesViewModelTest : BaseUnitTest() {

    private val viewModel by inject<SearchRepositoriesViewModel>()

    @Before
    fun setup() {
        loadKoin {
            single(override = true) { mockk<IGetRepositoriesUseCase>(relaxed = true) }
        }
    }

    @Test
    fun `fetchRepositories success behavior`() {
        // given
        val useCaseMock: IGetRepositoriesUseCase by inject()
        val capturedStates = mutableListOf<State<FetchRepositoriesResult>>()
        val observerSpy = TestUtils.createStateSpyObserver<FetchRepositoriesResult>()
        val dataMock = mockk<FetchRepositoriesResult>(relaxed = true) {
            every { repositories } returns listOf(mockk())
            every { paginationData } returns mockk()
        }
        viewModel.getRepositories().observeForever(observerSpy)
        every { useCaseMock.execute(any()) } returns flowOf(dataMock)

        // when
        viewModel.fetchRepositories()

        // then
        verify { useCaseMock.execute(any()) }
        verify { observerSpy.onChanged(capture(capturedStates)) }

        val showLoadingState = capturedStates[0] as State.Loading
        val successState = capturedStates[1] as State.Success
        val hideLoadingState = capturedStates[2] as State.Loading

        verifyOrder {
            observerSpy.onChanged(showLoadingState)
            observerSpy.onChanged(successState)
            observerSpy.onChanged(hideLoadingState)
        }

        Truth.assertThat(showLoadingState.isLoading).isTrue()
        Truth.assertThat(hideLoadingState.isLoading).isFalse()
        Truth.assertThat(successState.data).isEqualTo(dataMock)
        Truth.assertThat(viewModel.paginationData).isNotNull()
    }

    @Test
    fun `fetchRepositories error behavior`() {
        // given
        val useCaseMock: IGetRepositoriesUseCase by inject()
        val observerSpy = TestUtils.createStateSpyObserver<FetchRepositoriesResult>()
        viewModel.getRepositories().observeForever(observerSpy)
        val error = UnknownHostException()
        every { useCaseMock.execute(any()) } returns flow { throw error }

        // when
        viewModel.fetchRepositories()

        // then
        verify { useCaseMock.execute(any()) }
        val capturedStates = mutableListOf<State<FetchRepositoriesResult>>()
        coVerify { observerSpy.onChanged(capture(capturedStates)) }

        val showLoadingState = capturedStates[0] as State.Loading
        val hideLoadingState = capturedStates[1] as State.Loading
        val errorState = capturedStates[2] as State.Error

        verifyOrder {
            observerSpy.onChanged(showLoadingState)
            observerSpy.onChanged(hideLoadingState)
            observerSpy.onChanged(errorState)
        }

        Truth.assertThat(showLoadingState.isLoading).isTrue()
        Truth.assertThat(hideLoadingState.isLoading).isFalse()
        Truth.assertThat(errorState.error).isInstanceOf(NetworkError.NotConnected::class.java)
    }

    @Test
    fun `should not fetch repositories when its last page`() {
        // given
        val useCaseMock: IGetRepositoriesUseCase by inject()
        viewModel.paginationData = PaginationData(
            hasNextPage = false,
            endCursor = null
        )
        // when
        viewModel.fetchRepositories()

        // then
        verify {
            useCaseMock wasNot Called
        }
    }

    @Test
    fun `should set null on pagination data when reset page`() {
        // given
        viewModel.paginationData = PaginationData(
            hasNextPage = false,
            endCursor = null
        )
        // when
        viewModel.resetPage()

        // then
        Truth.assertThat(viewModel.paginationData).isNull()
    }

    @Test
    fun `should set selected language filter flag unique value on language list`() {
        // when
        viewModel.setLanguageFilter(Language.Dart)

        // then
        Truth.assertThat(viewModel.languagesList.find { it.language == Language.Dart }?.isSelected)
            .isTrue()
        Truth.assertThat(viewModel.languagesList.filter { it.isSelected }.size == 1).isTrue()
    }
}