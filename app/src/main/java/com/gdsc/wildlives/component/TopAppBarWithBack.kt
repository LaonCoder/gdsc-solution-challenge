package com.gdsc.wildlives.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.ui.theme.dark_gray
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun TopAppBarWithBack(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(start = 10.dp, top = 10.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "On Back",
                tint = ghost_white,
                modifier = Modifier.size(50.dp)
            )
        }

    }
}

@Preview
@Composable
fun TopAppBarWithBackPrev() {
    TopAppBarWithBack(navController = rememberNavController())
}