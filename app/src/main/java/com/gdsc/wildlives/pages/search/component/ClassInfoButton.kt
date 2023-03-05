package com.gdsc.wildlives.pages.search.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc.wildlives.pages.search.SearchUiState
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun ClassInfoButton(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?,
    title: String,
    textColor: Color,
    backgroundColor: Color
) {
    Button(
        onClick = {
            searchViewModel?.onAnimalClassChange(title)
        },
        shape = RoundedCornerShape(40.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (searchUiState?.currentAnimalClass.equals(title)) Color.Transparent
                    else Color.LightGray
        ),
        elevation = ButtonDefaults.elevation(3.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (searchUiState?.currentAnimalClass.equals(title)) backgroundColor
                              else ghost_white,
            contentColor = if (searchUiState?.currentAnimalClass.equals(title)) textColor
                           else Color.LightGray
        )
    ) {
        Text(text = title)
    }
}

@Composable
@Preview
fun SlideInfoButtonPrev() {
    ClassInfoButton(
        searchViewModel = null,
        searchUiState = null,
        title = "Mammals",
        textColor = Color.White,
        backgroundColor = colorPrimary
    )
}