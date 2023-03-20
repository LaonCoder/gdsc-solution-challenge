package com.gdsc.wildlives.pages.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.repository.StorageRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {
    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    val user = repository.user()

    val hasUser: Boolean
        get() = repository.hasUser()

    private val userId: String
        get() = repository.getUserId()

    fun loadUserProfile() {
        if (hasUser) {
            if (userId.isNotBlank()) {
                getUserProfile(userId)
            } else {
                Log.d("Load Profile", "Error")
            }
        }
    }

    private fun getUserProfile(userId: String) = viewModelScope.launch {
        repository.getUserProfile(userId).collect() { userProfile ->
            _profileUiState.update { it.copy(
                userId = userProfile.data!!.userId,
                userName = userProfile.data!!.name,
                userEmail = userProfile.data!!.email,
                userDescription = userProfile.data!!.description,
                userPoints = userProfile.data!!.points,
                userAnimalList = userProfile.data!!.animals
            ) }

            val (currentLevel, nextLevel, resPoint) = updateLevelInfo(userProfile.data!!.points)

            _profileUiState.update { it.copy(
                currentLevel = currentLevel,
                nextLevel = nextLevel,
                resPoint = resPoint
            ) }

            Log.d("Load Profile", "Successful")
        }
    }

    private fun updateLevelInfo(point: Int) = when {
            point >= 50000 -> listOf(9, 10, 0)
            point >= 30000 -> listOf(8, 9, 50000 - point)
            point >= 20000 -> listOf(7, 8, 30000 - point)
            point >= 15000 -> listOf(6, 7, 20000 - point)
            point >= 10000 -> listOf(5, 6, 15000 - point)
            point >= 6000 -> listOf(4, 5, 10000 - point)
            point >= 3000 -> listOf(3, 4, 6000 - point)
            point >= 1000 -> listOf(2, 3, 3000 - point)
            else -> listOf(1, 2, 1000 - point)
    }
}

data class ProfileUiState(
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val userDescription: String = "",
    val userPoints: Int = 0,
    val userAnimalList: List<AnimalData> = listOf(),

    val currentLevel: Int = 0,
    val nextLevel: Int = 0,
    val resPoint: Int = 0,
)

