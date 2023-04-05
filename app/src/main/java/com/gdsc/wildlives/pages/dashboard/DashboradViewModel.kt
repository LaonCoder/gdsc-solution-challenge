package com.gdsc.wildlives.pages.dashboard

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboradViewModel : ViewModel() {

    private val _dashboardUiState = MutableStateFlow(DashboardUiState())
    val dashboardUiState = _dashboardUiState.asStateFlow()

    fun onClassifiedChanged(
        onPhotoTaken: Boolean,
        classified: String,
        currentTime: String,
        bitmapImage: Bitmap?
    ) {
        _dashboardUiState.update {
            it.copy(
                onPhotoTaken = true,
                classified = classified,
                currentTime = currentTime,
                bitmapImage = bitmapImage,
            )
        }
    }

    fun resetState() {
        _dashboardUiState.update {
            it.copy(
                onPhotoTaken = false,
                classified = "",
                currentTime = "",
                bitmapImage = null,
            )
        }
    }
}

data class DashboardUiState(
    val onPhotoTaken: Boolean = false,
    val currentTime: String = "",
    val classified: String = "",
    val bitmapImage: Bitmap? = null
)