package br.com.lsm.androidsample.search

import br.com.lsm.androidsample.core.BaseUnitTest
import br.com.lsm.androidsample.core.TestUtils
import br.com.lsm.androidsample.domain.entity.*
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.core.State
import com.google.common.truth.Truth
import io.mockk.*
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

class SearchRepositoriesViewModelTest : BaseUnitTest() {

    private val viewModel: SearchRepositoriesViewModel by inject()
    private val getRepositoriesUseCaseMock: IGetRepositoriesUseCase by inject()

    @Before
    fun setMocks() {
        setupKoin {
            single<IGetRepositoriesUseCase>(override = true) { mockk() }
        }
    }

    @Test
    fun `fetchRepositories success behavior`() {
        val observerMock = TestUtils.createSpyObserver<State<FetchRepositoriesResult>>()
        viewModel.getRepositories().observeForever(observerMock)

        val data = mockk<FetchRepositoriesResult>(relaxed = true)

        every { getRepositoriesUseCaseMock.execute(any()) } returns Single.just(data)
        viewModel.fetchRepositories()

        verify {
            getRepositoriesUseCaseMock.execute(any())
        }

        val slots = mutableListOf<State<FetchRepositoriesResult>>()
        verify { observerMock.onChanged(capture(slots)) }

        val showLoadingState = slots[0] as State.Loading
        val hideLoadingState = slots[1] as State.Loading
        val successState = slots[2] as State.Success

        Truth.assertThat(showLoadingState.isLoading).isTrue()
        Truth.assertThat(hideLoadingState.isLoading).isFalse()
        Truth.assertThat(successState.data).isEqualTo(data)
        Truth.assertThat(viewModel.disposables.size() > 0).isTrue()
        Truth.assertThat(viewModel.paginationData).isNotNull()
    }

    @Test
    fun `fetchRepositories error behavior`() {
        val observerMock = TestUtils.createSpyObserver<State<FetchRepositoriesResult>>()
        viewModel.getRepositories().observeForever(observerMock)

        val error = Exception()

        every { getRepositoriesUseCaseMock.execute(any()) } returns Single.error(error)
        viewModel.fetchRepositories()

        verify {
            getRepositoriesUseCaseMock.execute(any())
        }

        val slots = mutableListOf<State<FetchRepositoriesResult>>()
        verify { observerMock.onChanged(capture(slots)) }

        val showLoadingState = slots[0] as State.Loading
        val hideLoadingState = slots[1] as State.Loading
        val errorState = slots[2] as State.Error

        Truth.assertThat(showLoadingState.isLoading).isTrue()
        Truth.assertThat(hideLoadingState.isLoading).isFalse()
        Truth.assertThat(errorState.error).isEqualTo(error)
        Truth.assertThat(viewModel.disposables.size() > 0).isTrue()
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

        Truth.assertThat(viewModel.disposables.size() == 0).isTrue()
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