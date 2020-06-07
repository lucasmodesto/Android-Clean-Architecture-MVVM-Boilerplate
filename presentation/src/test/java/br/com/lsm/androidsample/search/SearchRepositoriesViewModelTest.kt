package br.com.lsm.androidsample.search

import br.com.lsm.androidsample.core.BaseUnitTest
import br.com.lsm.androidsample.core.State
import br.com.lsm.androidsample.core.TestUtils
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
import org.koin.test.inject
import java.net.UnknownHostException

class SearchRepositoriesViewModelTest : BaseUnitTest() {

    private val viewModel: SearchRepositoriesViewModel by inject()
    private val getRepositoriesUseCaseMock: IGetRepositoriesUseCase = mockk()

    @Before
    fun setMocks() {
        loadKoin {
            single(override = true) { getRepositoriesUseCaseMock }
        }
    }

    @Test
    fun `fetchRepositories success behavior`() {
        // given
        val observerMock = TestUtils.createStateSpyObserver<FetchRepositoriesResult>()
        val capturedStates = mutableListOf<State<FetchRepositoriesResult>>()
        val dataMock = mockk<FetchRepositoriesResult>(relaxed = true) {
            every { repositories } returns listOf(mockk())
            every { paginationData } returns mockk()
        }
        viewModel.getRepositories().observeForever(observerMock)
        every { getRepositoriesUseCaseMock.execute(any()) } returns flowOf(dataMock)

        // when
        viewModel.fetchRepositories()

        // then
        verify { getRepositoriesUseCaseMock.execute(any()) }
        verify { observerMock.onChanged(capture(capturedStates)) }

        val showLoadingState = capturedStates[0] as State.Loading
        val successState = capturedStates[1] as State.Success
        val hideLoadingState = capturedStates[2] as State.Loading

        verifyOrder {
            observerMock.onChanged(showLoadingState)
            observerMock.onChanged(successState)
            observerMock.onChanged(hideLoadingState)
        }

        Truth.assertThat(showLoadingState.isLoading).isTrue()
        Truth.assertThat(hideLoadingState.isLoading).isFalse()
        Truth.assertThat(successState.data).isEqualTo(dataMock)
        Truth.assertThat(viewModel.paginationData).isNotNull()
    }

    @Test
    fun `fetchRepositories error behavior`() {
        // given
        val observerMock = TestUtils.createStateSpyObserver<FetchRepositoriesResult>()
        viewModel.getRepositories().observeForever(observerMock)
        val error = UnknownHostException()
        every { getRepositoriesUseCaseMock.execute(any()) } returns flow {
            throw error
        }

        // when
        viewModel.fetchRepositories()

        // then
        verify { getRepositoriesUseCaseMock.execute(any()) }
        val capturedStates = mutableListOf<State<FetchRepositoriesResult>>()
        verify { observerMock.onChanged(capture(capturedStates)) }

        val showLoadingState = capturedStates[0] as State.Loading
        val hideLoadingState = capturedStates[1] as State.Loading
        val errorState = capturedStates[2] as State.Error

        verifyOrder {
            observerMock.onChanged(showLoadingState)
            observerMock.onChanged(hideLoadingState)
            observerMock.onChanged(errorState)
        }

        Truth.assertThat(showLoadingState.isLoading).isTrue()
        Truth.assertThat(hideLoadingState.isLoading).isFalse()
        Truth.assertThat(errorState.error).isEqualTo(error)
    }

    @Test
    fun `should not fetch repositories when its last page`() {
        viewModel.paginationData = PaginationData(
            hasNextPage = false,
            endCursor = null
        )
        viewModel.fetchRepositories()
        verify {
            getRepositoriesUseCaseMock wasNot Called
        }
    }

    @Test
    fun `should set null on pagination data when reset page`() {
        viewModel.paginationData = PaginationData(
            hasNextPage = false,
            endCursor = null
        )
        viewModel.resetPage()
        Truth.assertThat(viewModel.paginationData).isNull()
    }

    @Test
    fun `should set selected language filter flag unique value on language list`() {
        viewModel.setLanguageFilter(Language.Dart)
        Truth.assertThat(viewModel.languagesList.find { it.language == Language.Dart }?.isSelected)
            .isTrue()
        Truth.assertThat(viewModel.languagesList.filter { it.isSelected }.size == 1).isTrue()
    }
}