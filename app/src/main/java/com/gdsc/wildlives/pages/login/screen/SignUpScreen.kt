package com.gdsc.wildlives.pages.login

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.BuildConfig
import com.gdsc.wildlives.R
import com.gdsc.wildlives.navigation.Screen
import com.gdsc.wildlives.pages.login.component.ErrorDialog
import com.gdsc.wildlives.pages.login.component.LoginHeader
import com.gdsc.wildlives.ui.theme.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun SignUpScreen(
    navController: NavController,
    loginViewModel: LoginViewModel?
) {
    val loginUiState = loginViewModel?.loginUiState?.collectAsState()?.value
    var isError = loginUiState?.signUpError != null
    val context = LocalContext.current


    // Loading Screen
    if (loginUiState?.isLoading == true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorPrimary
                )
            }
        }
    }
    // Sign In Screen
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = ghost_white)
        ) {
            ConstraintLayout {

                val (logoimageref, signupformref) = createRefs()

                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .constrainAs(logoimageref) {
                            top.linkTo(signupformref.top)
                            bottom.linkTo(signupformref.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                    LoginHeader()
                }

                Surface(
                    color = ghost_white,
                    shape = RoundedCornerShape(40.dp).copy(
                        bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp)
                        .constrainAs(signupformref) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            colorPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("Create ")
                                    }

                                    withStyle(
                                        style = SpanStyle(
                                            dark_gray,
                                            fontWeight = FontWeight.Normal
                                        )
                                    ) {
                                        append("your new account")
                                    }

                                },
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Login Error Alert Dialog
                        if (isError) {
                            ErrorDialog(
                                loginViewModel = loginViewModel,
                                loginUiState = loginUiState,
                                title = "Sign Up Error"
                            )
                        }

                        Spacer(modifier = Modifier.padding(10.dp))

                        TextField(
                            value = loginUiState?.userNameSignUp ?: "",
                            leadingIcon = {
                                Row(
                                    modifier = Modifier.wrapContentWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Icon(
                                            modifier = Modifier.padding(start = 10.dp),
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            tint = colorPrimary
                                        )
                                        Canvas(
                                            modifier = Modifier
                                                .height(24.dp)
                                                .padding(horizontal = 10.dp)
                                        ) {
                                            drawLine(
                                                color = light_gray,
                                                start = Offset(0f, 0f),
                                                end = Offset(0f, size.height),
                                                strokeWidth = 2.0F
                                            )
                                        }
                                    }
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = white,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            label = { Text(text = "User name") },
                            shape = RoundedCornerShape(16.dp),
                            onValueChange = { loginViewModel?.onUserNameSignUpChange(it) },
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = loginUiState?.emailSignUp ?: "",
                            leadingIcon = {
                                Row(
                                    modifier = Modifier.wrapContentWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Icon(
                                            modifier = Modifier.padding(start = 10.dp),
                                            imageVector = Icons.Default.Email,
                                            contentDescription = null,
                                            tint = colorPrimary
                                        )
                                        Canvas(
                                            modifier = Modifier
                                                .height(24.dp)
                                                .padding(horizontal = 10.dp)
                                        ) {
                                            drawLine(
                                                color = light_gray,
                                                start = Offset(0f, 0f),
                                                end = Offset(0f, size.height),
                                                strokeWidth = 2.0F
                                            )
                                        }
                                    }
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = white,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            label = { Text(text = "Email address") },
                            shape = RoundedCornerShape(16.dp),
                            onValueChange = { loginViewModel?.onEmailSignUpChange(it) }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = loginUiState?.passwordSignUp ?: "",
                            leadingIcon = {
                                Row(
                                    modifier = Modifier.wrapContentWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Icon(
                                            modifier = Modifier.padding(start = 10.dp),
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = colorPrimary
                                        )
                                        Canvas(
                                            modifier = Modifier
                                                .height(24.dp)
                                                .padding(horizontal = 10.dp)
                                        ) {
                                            drawLine(
                                                color = light_gray,
                                                start = Offset(0f, 0f),
                                                end = Offset(0f, size.height),
                                                strokeWidth = 2.0F
                                            )
                                        }
                                    }
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = white,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            label = { Text(text = "Password") },
                            shape = RoundedCornerShape(16.dp),
                            onValueChange = { loginViewModel?.onPasswordSignUpChange(it) },
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = loginUiState?.confirmPasswordSignUp ?: "",
                            leadingIcon = {
                                Row(
                                    modifier = Modifier.wrapContentWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Icon(
                                            modifier = Modifier.padding(start = 10.dp),
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = colorPrimary
                                        )
                                        Canvas(
                                            modifier = Modifier
                                                .height(24.dp)
                                                .padding(horizontal = 10.dp)
                                        ) {
                                            drawLine(
                                                color = light_gray,
                                                start = Offset(0f, 0f),
                                                end = Offset(0f, size.height),
                                                strokeWidth = 2.0F
                                            )
                                        }
                                    }
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = white,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            label = { Text(text = "Confirm password") },
                            shape = RoundedCornerShape(16.dp),
                            onValueChange = { loginViewModel?.onConfirmPasswordSignUpChange(it) },
                        )


                        Button(
                            onClick = { loginViewModel?.createUser(context) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 20.dp,
                                    bottom = 25.dp
                                )
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Create Account",
                                fontSize = 18.sp,
                                color = white,
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                            ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Already have an Account? Sign In")
                                    addStyle(
                                        SpanStyle(color = colorPrimary),
                                        25,
                                        this.length
                                    )
                                },
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.subtitle1,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.LoginScreen.route) {
                                        launchSingleTop = true
                                        popUpTo(route = Screen.SignUpScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = loginViewModel?.hasUser) {
        if (loginViewModel?.hasUser == true) {
            navController.popBackStack()
            navController.navigate(Screen.HomeScreen.route)
        }
    }
}


@Preview
@Composable
fun SignUpScreenPrev() {
    SignUpScreen(navController = rememberNavController(), loginViewModel = null)
}