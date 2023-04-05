package com.gdsc.wildlives.pages.detail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gdsc.wildlives.data.AnimalData
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.pages.detail.DetailUiState
import com.gdsc.wildlives.pages.detail.DetailViewModel
import com.gdsc.wildlives.pages.login.LoginUiState
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun AskSaveDialog(
    detailViewModel: DetailViewModel?,
    navController: NavController,
    animalData: AnimalData,
) {
    AlertDialog(
        shape = RoundedCornerShape(18.dp),
        onDismissRequest = {
            detailViewModel?.openSaveDialog(false)
            navController.popBackStack()
        },
        title = {
            Text(
                text = "Save in my encyclopedia?",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        },
        text = {
            Text(
                text = animalData!!.name,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    detailViewModel?.saveToMyProfile(animalData = animalData)
                    detailViewModel?.openSaveDialog(false)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    detailViewModel?.openSaveDialog(false)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ghost_white,
                    contentColor = Color.LightGray
                ),
                border = BorderStroke(2.dp, Color.LightGray),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
            ) {
                Text(
                    text = "Dismiss",
                    fontSize = 16.sp
                )
            }
        },
    )
}

@Composable
@Preview
fun ErrorDialogPrev() {
    com.gdsc.wildlives.pages.login.component.ErrorDialog(
        loginViewModel = null,
        loginUiState = null,
        title = "Error Name"
    )
}