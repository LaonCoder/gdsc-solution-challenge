package com.gdsc.wildlives.pages.login.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.wildlives.pages.login.LoginUiState
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.ui.theme.colorPrimary

@Composable
fun ErrorDialog(
    loginViewModel: LoginViewModel?,
    loginUiState: LoginUiState?,
    title: String
) {
    AlertDialog(
        shape = RoundedCornerShape(18.dp),
        onDismissRequest = {
            loginViewModel?.clearError()
        },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        },
        text = {
            Text(
                text =
                    when(title) {
                        "Login Error" -> loginUiState?.loginError ?: "Unknown Login Error"
                        "Sign Up Error" -> loginUiState?.signUpError ?: "Unknown Sign Up Error"
                        else -> "Unknown Error"
                    },
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    loginViewModel?.clearError()
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
    )
}

@Composable
@Preview
fun ErrorDialogPrev() {
    ErrorDialog(loginViewModel = null, loginUiState = null, title = "Error Name")
}