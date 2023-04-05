package com.gdsc.wildlives.pages.encyclopedia

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.repository.StorageRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EncyclopediaViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {
    private val _encyclopediaUiState = MutableStateFlow(EncyclopediaUiState())
    val encyclopediaUiState = _encyclopediaUiState.asStateFlow()

    val user = repository.user()

    val hasUser: Boolean
        get() = repository.hasUser()

    private val userId: String
        get() = repository.getUserId()

    fun onSearchTextChange(text: String) {
        _encyclopediaUiState.update { it.copy(searchText = text, isSearching = true) }
    }

    fun onAnimalClassChange(currentAnimalClass: String?) {
        _encyclopediaUiState.update { uiState ->
            uiState.copy(
                currentAnimalClass = currentAnimalClass,
                currentAnimalList = encyclopediaUiState.value.userAnimalList
                    .filter {
                            // Search Text Filtering
                            it.name.contains(encyclopediaUiState.value.searchText.trim().lowercase())
                        &&  // Animal Class Filtering
                            it.animalClass == currentAnimalClass
                        &&  // Endangered Class Filtering
                            if (encyclopediaUiState.value.currentEndangeredClass == null) { true }
                            else { it.endangeredClass == encyclopediaUiState.value.currentEndangeredClass }
                    }
            )
        }
    }

    fun onRedListChange(currentEndangeredClass: String?) {
        _encyclopediaUiState.update { uiState ->
            uiState.copy(
                currentEndangeredClass = currentEndangeredClass,
                currentAnimalList = encyclopediaUiState.value.userAnimalList
                    .filter {
                            // Search Text Filtering
                            it.name.contains(encyclopediaUiState.value.searchText.trim().lowercase())
                        &&  // Animal Class Filtering
                            if (encyclopediaUiState.value.currentAnimalClass == null) { true }
                            else {
                                it.animalClass == encyclopediaUiState.value.currentAnimalClass
                            }
                        &&  // Endangered Class Filtering
                            it.endangeredClass == currentEndangeredClass
                    }
            )
        }
    }

    fun loadUserAnimalList() {
        if (hasUser) {
            if (userId.isNotBlank()) {
                getUserAnimalList(userId)
            } else {
                Log.d("Load Profile", "Error")
            }
        }
    }

    private fun getUserAnimalList(userId: String) = viewModelScope.launch {
        repository.getUserProfile(userId).collect() { userProfile ->
            _encyclopediaUiState.update { it.copy(
                userAnimalList = userProfile.data!!.animals
            ) }

            Log.d("Load User AnimalList", "Successful")
        }
    }

    init {
        encyclopediaUiState
            .debounce(500L)
            .onEach {
                if (encyclopediaUiState.value.searchText.isBlank()) {
                    _encyclopediaUiState.update { uiState ->
                        uiState.copy(
                            currentAnimalList = encyclopediaUiState.value.userAnimalList
                                .filter {
                                        if (encyclopediaUiState.value.currentAnimalClass == null) { true }
                                        else {
                                            it.animalClass == encyclopediaUiState.value.currentAnimalClass
                                        }
                                    &&  // Endangered Class Filtering
                                        if (encyclopediaUiState.value.currentEndangeredClass == null) { true }
                                        else {
                                            it.endangeredClass == encyclopediaUiState.value.currentEndangeredClass
                                        }
                                },
                            isSearching = false)
                    }
                }
                else {
                    _encyclopediaUiState.update { uiState ->
                        uiState.copy(
                            currentAnimalList = encyclopediaUiState.value.userAnimalList
                                .filter {
                                    // Search Text Filtering
                                    it.name.contains(encyclopediaUiState.value.searchText.trim().lowercase())
                                &&  // Animal Class Filtering
                                    if (encyclopediaUiState.value.currentAnimalClass == null) { true }
                                    else { it.animalClass.equals(encyclopediaUiState.value.currentAnimalClass) }
                                &&  // Endangered Class Filtering
                                    if (encyclopediaUiState.value.currentEndangeredClass == null) { true }
                                    else { it.endangeredClass.equals(encyclopediaUiState.value.currentEndangeredClass) }
                                },
                            isSearching = false
                        )
                    }
                }
            }
            .distinctUntilChanged()
            .launchIn(viewModelScope)
    }
}

data class EncyclopediaUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,

    val currentAnimalClass: String? = null,
    val currentEndangeredClass: String? = null,

    val currentAnimalList: List<AnimalData> = listOf(),
    val userAnimalList: List<AnimalData> = listOf()
)

