package com.gdsc.wildlives.pages.donate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.wildlives.MainActivity
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.WebsiteData
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.navigation.Screen
import com.gdsc.wildlives.repository.StorageRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class DonateViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {
    private val _donateUiState = MutableStateFlow(DonateUiState())
    val donateUiState = _donateUiState.asStateFlow()

    fun loadWebsiteData() {
        val websiteDataList = arrayListOf<WebsiteData>()
        val db = Firebase.firestore
        db.collection("WEBSITE")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    websiteDataList.add(document.toObject())
                }
                _donateUiState.update { uiState ->
                    uiState.copy(websiteInfoList = websiteDataList)
                }
                Log.d("Fetch Website Data From Firebase", donateUiState.value.websiteInfoList.toString())
            }
            .addOnFailureListener { exception ->
                Log.d("Fetch Website Data From Firebase", exception.stackTraceToString())
                MainActivity().finish()
                exitProcess(1)
            }
    }
}

data class DonateUiState(
    val websiteInfoList: List<WebsiteData> = listOf()
)