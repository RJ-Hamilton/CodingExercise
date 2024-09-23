package com.hamilton.codingexercise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hamilton.codingexercise.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = {
                viewModel.refreshData()
            }
        ) {
            LazyColumn(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.background
                )
            ) {
                itemsIndexed(state.hiringDetailUiModels) { index, dataUiModel ->
                    val paddingValues = when (index) {
                        0 -> PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
                        state.hiringDetailUiModels.lastIndex -> PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 48.dp
                        )

                        else -> PaddingValues(start = 16.dp, end = 16.dp, bottom = 4.dp)
                    }

                    HiringDetailItemView(
                        modifier = Modifier.padding(paddingValues),
                        hiringDetailUiModel = dataUiModel
                    )
                }
            }
        }
    }
}