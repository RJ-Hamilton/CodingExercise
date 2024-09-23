package com.hamilton.codingexercise

import com.hamilton.codingexercise.ui.models.HiringDetailUiModel
import com.hamilton.services.hiring.api.HiringRepository
import com.hamilton.services.hiring.api.models.domain.HiringDetail
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val mockHiringRepository: HiringRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        viewModel = MainViewModel(
            hiringRepository = mockHiringRepository,
            coroutineDispatcher = testDispatcher
        )
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `GIVEN a successful data fetch, WHEN fetchData is called, THEN uiState should update with hiringDetailUiModels and isLoading should be false`() =
        runTest {
            // GIVEN
            val hiringDetails = listOf(
                HiringDetail(id = 1, listId = 2, name = "Item 1"),
                HiringDetail(id = 2, listId = 1, name = "Item 2")
            )
            coEvery { mockHiringRepository.getData() } returns hiringDetails

            val testResults = mutableListOf<State>()

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(testResults)
            }

            // WHEN
            viewModel.fetchData()

            // THEN
            val expectedUiModels = listOf(
                HiringDetailUiModel(listId = 1, name = "2"),
                HiringDetailUiModel(listId = 2, name = "1")
            )

            assertEquals(3, testResults.size)
            assertEquals(
                expected = State(
                    isLoading = false,
                    showErrorMessage = false,
                    hiringDetailUiModels = emptyList()
                ),
                actual = testResults[0]
            )
            assertEquals(
                expected = State(
                    isLoading = true,
                    showErrorMessage = false,
                    hiringDetailUiModels = emptyList()
                ),
                actual = testResults[1]
            )
            assertEquals(
                expected = State(
                    isLoading = false,
                    showErrorMessage = false,
                    hiringDetailUiModels = expectedUiModels
                ),
                actual = testResults[2]
            )
        }

    @Test
    fun `GIVEN an exception, WHEN fetchData is called, THEN uiState should update with showErrorMessage and isLoading should be false`() =
        runTest {
            // GIVEN
            coEvery { mockHiringRepository.getData() } throws RuntimeException("API call failed")

            val testResults = mutableListOf<State>()

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(testResults)
            }

            // WHEN
            viewModel.fetchData()

            // THEN
            assertEquals(3, testResults.size)
            assertEquals(
                expected = State(
                    isLoading = false,
                    showErrorMessage = false,
                    hiringDetailUiModels = emptyList()
                ),
                actual = testResults[0]
            )
            assertEquals(
                expected = State(
                    isLoading = true,
                    showErrorMessage = false,
                    hiringDetailUiModels = emptyList()
                ),
                actual = testResults[1]
            )
            assertEquals(
                expected = State(
                    isLoading = false,
                    showErrorMessage = true,
                    hiringDetailUiModels = emptyList()
                ),
                actual = testResults[2]
            )
        }
}