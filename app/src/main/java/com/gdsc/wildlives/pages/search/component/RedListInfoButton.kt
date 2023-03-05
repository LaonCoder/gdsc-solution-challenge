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
import com.gdsc.wildlives.ui.theme.ghost_white
import com.gdsc.wildlives.ui.theme.iucn_ex_color

@Composable
fun RedListInfoButton(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?,
    title: String,
    textColor: Color,
    backgroundColor: Color
) {
    Button(
        onClick = {
            searchViewModel?.onRedListChange(title)
        },
        shape = RoundedCornerShape(40.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (searchUiState?.currentRedListCategory.equals(title)) Color.Transparent
                    else Color.LightGray
        ),
        elevation = ButtonDefaults.elevation(3.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (searchUiState?.currentRedListCategory.equals(title)) backgroundColor
                              else ghost_white,
            contentColor = if (searchUiState?.currentRedListCategory.equals(title)) textColor
                           else Color.LightGray
        )
    ) {
        Text(text = title)
    }
}

@Composable
@Preview
fun RedListInfoButtonPrev() {
    RedListInfoButton(
        searchViewModel = null,
        searchUiState = null,
        title = "EN",
        textColor = Color.White,
        backgroundColor = iucn_ex_color
    )
}