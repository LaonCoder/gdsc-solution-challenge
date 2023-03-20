package com.gdsc.wildlives.pages.search.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc.wildlives.pages.search.SearchUiState
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.endangered_class_1_color
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun EndangeredClassInfoButton(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?,
    title: String,
    textColor: Color,
    backgroundColor: Color
) {
    Button(
        modifier = Modifier
            .width(110.dp),
        onClick = {
            searchViewModel?.onRedListChange(title)
        },
        shape = RoundedCornerShape(40.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (searchUiState?.currentEndangeredClass.equals(title)) Color.Transparent
                    else Color.LightGray
        ),
        elevation = ButtonDefaults.elevation(3.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (searchUiState?.currentEndangeredClass.equals(title)) backgroundColor
                              else ghost_white,
            contentColor = if (searchUiState?.currentEndangeredClass.equals(title)) textColor
                           else Color.LightGray
        )
    ) {
        Text(
            text = when(title) {
                "ONE" -> "Class 1"
                "TWO" -> "Class 2"
                else -> "NONE"
            }
        )
    }
}

@Composable
@Preview
fun EndangeredClassInfoButtonPrev() {
    EndangeredClassInfoButton(
        searchViewModel = null,
        searchUiState = null,
        title = "1",
        textColor = Color.White,
        backgroundColor = endangered_class_1_color
    )
}