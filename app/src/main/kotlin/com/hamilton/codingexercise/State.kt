package com.hamilton.codingexercise

import com.hamilton.codingexercise.ui.models.HiringDetailUiModel

data class State(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val showErrorMessage: Boolean = false,
    val hiringDetailUiModels: List<HiringDetailUiModel> = emptyList()
)
