package com.gdsc.wildlives.pages.detail


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.wildlives.data.AnimalClass
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.animalData
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
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