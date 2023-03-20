package com.gdsc.wildlives.pages.dashboard

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboradViewModel : ViewModel() {

    private val _dashboardUiState = MutableStateFlow(DashboardUiState())
    val dashboardUiState = _dashboardUiState.asStateFlow()

    fun onClassifiedChanged(classified: String, bitmapImage: Bitmap?) {
        _dashboardUiState.update { it.copy(classified = classified, bitmapImage = bitmapImage) }
    }
}

data class DashboardUiState(
    var onPhotoTaken: Boolean = false,
    var classified: String = "",
    var bitmapImage: Bitmap? = null
)