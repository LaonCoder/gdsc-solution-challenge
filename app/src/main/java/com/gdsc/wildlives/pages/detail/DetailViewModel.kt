package com.gdsc.wildlives.pages.detail


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.wildlives.data.AnimalClass
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.repository.StorageRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.*

class DetailViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    fun saveToMyProfile(animalData: AnimalData) {
        repository.saveAnimalDataToUserProfile(animalData = animalData)
    }

    fun openSaveDialog(isSaveDialogOpen: Boolean) {
        _detailUiState.update { it.copy(isSaveDialogOpen = isSaveDialogOpen) }
    }
}

data class DetailUiState(
    val animalName: String = "",
    val isSaveDialogOpen: Boolean = false,
)