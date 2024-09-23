package com.hamilton.codingexercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamilton.codingexercise.ui.models.HiringDetailUiModel
import com.hamilton.services.hiring.api.HiringRepository
import com.hamilton.services.hiring.api.models.domain.HiringDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val hiringRepository: HiringRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    /**
     * Fetches and processes hiring data, updating the UI state accordingly.
     *
     * This function triggers a data fetch from the repository, processes the data, and updates the
     * UI state with the result. It runs asynchronously in a coroutine using the `viewModelScope`.
     * The process includes:
     *
     * 1. Setting the `isLoading` flag to `true` before starting the data fetch.
     * 2. Fetching data from the `hiringRepository` and applying filtering and sorting using the
     * `filterAndSort()` method.
     * 3. Mapping the fetched `HiringDetail` data to `HiringDetailUiModel` objects, transforming
     * the `name` by removing a prefix (via `removeItemText()`).
     * 4. Updating the `_uiState` with the resulting list of `HiringDetailUiModel` objects and
     * setting `isLoading` to `false`.
     * 5. If an exception occurs, the `showErrorMessage` flag is set to `true` and `isLoading` is
     * set to `false`.
     *
     * @throws Exception In case of an error during data fetching or processing.
     */
    fun fetchData() {
        updateLoadingState(isLoading = true)

        viewModelScope.launch(coroutineDispatcher) {
            try {
                val hiringDetailsMap = hiringRepository.getData().filterAndSort()

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        hiringDetailUiModels = hiringDetailsMap.map {
                            HiringDetailUiModel(
                                listId = it.listId,
                                name = it.name?.removeItemText().orEmpty()
                            )
                        }
                    )
                }
            } catch (exception: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        showErrorMessage = true
                    )
                }
            }
        }
    }

    /**
     * Updates the loading state in the UI state.
     *
     * This function modifies the `_uiState` to reflect whether a loading operation is in progress.
     * It copies the current UI state and sets the `isLoading` flag to the provided value.
     *
     * @param isLoading A Boolean indicating whether loading is in progress (`true`) or not
     * (`false`).
     */
    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }


    /**
     * Extension function to filter and sort a list of `HiringDetail` items.
     *
     * This function performs the following operations on the list of `HiringDetail` objects:
     *
     * 1. **Filter**: Removes any items where the `name` property is either `null` or an empty
     * string.
     * 2. **Sort**: After filtering, the remaining items are sorted using the
     * `sortByListIdAndName()` function, which sorts the list first by `listId`, and then by the
     * numeric part of the `name` property after removing a prefix (handled by `removeItemText()`
     * within `sortByListIdAndName()`).
     *
     * @receiver List<HiringDetail> The list of `HiringDetail` objects to be filtered and sorted.
     * @return List<HiringDetail> A new list of `HiringDetail` objects where:
     * - All items have a non-null, non-empty `name`.
     * - Items are sorted first by `listId` and then by the numeric value extracted from the `name`.
     */
    private fun List<HiringDetail>.filterAndSort(): List<HiringDetail> {
        return this
            .filter { !it.name.isNullOrEmpty() }
            .sortByListIdAndName()
    }

    private fun String.removeItemText(): String {
        return this.removePrefix("Item ")
    }


    /**
     * Extension function to sort a list of `HiringDetail` items.
     *
     * This function sorts the list of `HiringDetail` objects in two stages:
     * 1. First, it sorts by `listId` in ascending order.
     * 2. Then, within each `listId` group, it sorts by `name`, converting the numeric part of the
     * name (after removing a specified prefix, using the `removeItemText()` extension function)
     * into an integer for numerical sorting.
     *
     * @receiver List<HiringDetail> The list of `HiringDetail` objects to be sorted.
     * @return List<HiringDetail> A new list of `HiringDetail` objects, sorted first by `listId`,
     * then by the numeric value derived from the `name` property after applying the
     * `removeItemText()` transformation.
     */
    private fun List<HiringDetail>.sortByListIdAndName(): List<HiringDetail> {
        return sortedWith(compareBy({ it.listId }, { it.name?.removeItemText()?.toInt() }))
    }
}