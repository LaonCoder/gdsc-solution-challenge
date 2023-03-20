package com.gdsc.wildlives.pages.classification


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.util.*

@OptIn(FlowPreview::class)
class ClassificationViewModel : ViewModel() {

    private val _classificationUiState = MutableStateFlow(ClassificationUiState())
    val classificationUiState = _classificationUiState.asStateFlow()

    fun onAnimalNamePassed(animalName: String) {

    }

}

data class ClassificationUiState(
    val animalName: String = ""
)