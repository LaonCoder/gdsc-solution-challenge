package com.gdsc.wildlives.pages.search

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
class SearchViewModel : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchUiState.update { it.copy(searchText = text, isSearching = true) }
    }

    fun onAnimalClassChange(currentAnimalClass: String) {
        _searchUiState.update { uiState ->
            uiState.copy(
                currentAnimalClass = currentAnimalClass,
                currentAnimalClassList = animalData.filter { it.animalClass == currentAnimalClass }.shuffled()
            )
        }
    }

    fun onRedListChange(currentEndangeredClass: String) {
        _searchUiState.update { uiState ->
            uiState.copy(
                currentEndangeredClass = currentEndangeredClass,
                currentEndangeredClassList = animalData.filter { it.endangeredClass == currentEndangeredClass }.shuffled()
            )
        }
    }


    private fun getRandomAnimal(): List<AnimalData> {
        val listSize = animalData.size
        val calendar = Calendar.getInstance()
        val todayInt = (calendar.get(Calendar.YEAR) * 10000) + (calendar.get(Calendar.MONTH + 1) * 100) + (calendar.get(
            Calendar.DATE))

        Log.d("Today's Animal index", (todayInt % listSize).toString())
        return listOf(
            animalData[todayInt % listSize],
            animalData[(todayInt + 1) % listSize],
            animalData[(todayInt + 2) % listSize],
            animalData[(todayInt + 3) % listSize],
            animalData[(todayInt + 4) % listSize],
        )
    }

    fun createTodaysAnimalList() {
        _searchUiState.update { uiState ->
            uiState.copy(
                todaysAnimalList = getRandomAnimal()
            )
        }
    }

    init {
        searchUiState
            .debounce(500L)
            .onEach {
                if (searchUiState.value.searchText.isBlank()) {
                    _searchUiState.update { uiState ->
                        uiState.copy(animalList = listOf(), isSearching = false)
                    }
                }
                else {
                    _searchUiState.update { uiState ->
                        uiState.copy(
                            animalList = animalData.filter { it.name.contains(searchUiState.value.searchText.trim().lowercase()) },
                            isSearching = false
                        )
                    }
                }
            }
            .distinctUntilChanged()
            .launchIn(viewModelScope)
    }
}

data class SearchUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,

    val animalList: List<AnimalData> = listOf(),

    val currentAnimalClass: String = "",
    val currentAnimalClassList: List<AnimalData> = listOf(),

    val currentEndangeredClass: String = "",
    val currentEndangeredClassList: List<AnimalData> = listOf(),

    val todaysAnimalList: List<AnimalData> = listOf(),
)