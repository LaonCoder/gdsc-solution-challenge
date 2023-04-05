package com.gdsc.wildlives.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.pages.detail.DetailUiState
import com.gdsc.wildlives.pages.detail.DetailViewModel
import com.gdsc.wildlives.pages.detail.component.AskSaveDialog
import com.gdsc.wildlives.ui.theme.dark_gray
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun TopAppBarWithBack(
    navController: NavController,
    animalData: AnimalData? = null,
    detailViewModel: DetailViewModel? = null,
    detailUiState: DetailUiState? = null,
    isClassified: Boolean? = null,
) {
    if (detailUiState != null && animalData != null && detailUiState?.isSaveDialogOpen == true) {
        if (isClassified == true) {
            AskSaveDialog(
                detailViewModel = detailViewModel,
                navController = navController,
                animalData = animalData,
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(start = 10.dp, top = 10.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (isClassified == true) {
                    Log.d("Open Dialog", "true")
                    detailViewModel?.openSaveDialog(true)
                } else {
                    Log.d("Open Dialog", "false")
                    navController.popBackStack()
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "On Back",
                tint = ghost_white,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}