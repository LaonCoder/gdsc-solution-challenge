package com.gdsc.wildlives.pages.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gdsc.wildlives.data.EndangeredClassList
import com.gdsc.wildlives.ui.theme.endangered_class_1_color
import com.gdsc.wildlives.ui.theme.endangered_class_2_color


@Composable
fun EndangeredClassIcon(
    endangeredClass: String
) {
    if (endangeredClass == EndangeredClassList.ONE.name) {
        Box(
            modifier = Modifier
                .background(endangered_class_1_color, RoundedCornerShape(10.dp))
                .padding(vertical = 2.dp, horizontal = 10.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CLASS 1",
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        }
    } else if (endangeredClass == EndangeredClassList.TWO.name) {
        Box(
            modifier = Modifier
                .background(endangered_class_2_color)
                .padding(20.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CLASS 2",
                color = Color.White,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }
    }
}