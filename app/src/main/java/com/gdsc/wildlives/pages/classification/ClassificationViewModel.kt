package com.gdsc.wildlives.pages.detail


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.util.*

@OptIn(FlowPreview::class)
class DetailViewModel : ViewModel() {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    fun onAnimalNamePassed(animalName: String) {

    }

}

data class DetailUiState(
    val animalName: String = ""
)