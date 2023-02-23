package com.gdsc.wildlives.pages.search

import androidx.lifecycle.ViewModel
import com.gdsc.wildlives.pages.login.LoginUiState
import com.gdsc.wildlives.repository.AuthRepository
import com.gdsc.wildlives.repository.StorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val repository: StorageRepository = StorageRepository(),
): ViewModel() {
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    fun onSearchKeywordChange(searchKeyword: String) {
        _searchUiState.update { it.copy(searchKeyword = searchKeyword) }
    }
}

data class SearchUiState(
    val searchKeyword: String = "",
)