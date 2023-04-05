package com.gdsc.wildlives.pages.encyclopedia.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc.wildlives.pages.encyclopedia.EncyclopediaUiState
import com.gdsc.wildlives.pages.encyclopedia.EncyclopediaViewModel
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun CategoryInfoButton(
    encyclopediaViewModel: EncyclopediaViewModel?,
    encyclopediaUiState: EncyclopediaUiState?,
    title: String,
    isAnimalClass: Boolean = true
) {
    Button(
        onClick = {
            if (isAnimalClass) {
                if (encyclopediaUiState?.currentAnimalClass.equals(title)) {
                    encyclopediaViewModel?.onAnimalClassChange(null)
                } else {
                    encyclopediaViewModel?.onAnimalClassChange(title)
                }
            }  // else isEndangeredAnimalClass
            else {
                if (encyclopediaUiState?.currentEndangeredClass.equals(title)) {
                    encyclopediaViewModel?.onRedListChange(null)
                } else {
                    encyclopediaViewModel?.onRedListChange(title)
                }
            }
        },
        shape = RoundedCornerShape(40.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (
                if (isAnimalClass) {
                    encyclopediaUiState?.currentAnimalClass != null &&
                    encyclopediaUiState.currentAnimalClass == title
                } else {
                    encyclopediaUiState?.currentEndangeredClass != null &&
                    encyclopediaUiState.currentEndangeredClass == title
                }
            ) ghost_white
            else Color.Transparent
        ),
        elevation = ButtonDefaults.elevation(3.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (
                if (isAnimalClass) {
                    encyclopediaUiState?.currentAnimalClass != null &&
                            encyclopediaUiState.currentAnimalClass == title
                } else {
                    encyclopediaUiState?.currentEndangeredClass != null &&
                            encyclopediaUiState.currentEndangeredClass == title
                }
            ) {
                Color.Transparent
            } else {
                ghost_white
            },
            contentColor = if (
                if (isAnimalClass) {
                    encyclopediaUiState?.currentAnimalClass != null &&
                            encyclopediaUiState.currentAnimalClass == title
                } else {
                    encyclopediaUiState?.currentEndangeredClass != null &&
                            encyclopediaUiState.currentEndangeredClass == title
                }
            ) {
                ghost_white
            } else {
                Color.LightGray
            }
        )
    ) {
        Text(text = title)
    }
}

@Composable
@Preview
fun CategoryInfoButtonPrev() {
    CategoryInfoButton(
        encyclopediaViewModel = null,
        encyclopediaUiState = null,
        title = "Mammals",
        isAnimalClass = true
    )
}