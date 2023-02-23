package com.gdsc.wildlives.pages.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.wildlives.repository.AuthRepository
import com.gdsc.wildlives.repository.StorageRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository(),
): ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean
        get() = repository.hasUser()

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun onEmailChange(email: String) {
        _loginUiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _loginUiState.update { it.copy(password = password) }
    }

    fun onUserNameSignUpChange(userName: String) {
        _loginUiState.update { it.copy(userNameSignUp = userName) }
    }

    fun onEmailSignUpChange(email: String) {
        _loginUiState.update { it.copy(emailSignUp = email) }
    }

    fun onPasswordSignUpChange(password: String) {
        _loginUiState.update { it.copy(passwordSignUp = password) }
    }

    fun onConfirmPasswordSignUpChange(confirmPassword: String) {
        _loginUiState.update { it.copy(confirmPasswordSignUp = confirmPassword) }
    }

    private fun validateLoginForm() =
        loginUiState.value.email.isNotBlank() && loginUiState.value.email.isNotBlank()

    private fun validateUserNameForm() =
        loginUiState.value.userNameSignUp.isNotBlank()

    private fun validateSignUpForm() =
        loginUiState.value.emailSignUp.isNotBlank() &&
                loginUiState.value.passwordSignUp.isNotBlank() &&
                loginUiState.value.confirmPasswordSignUp.isNotBlank()

    fun clearError() {
        _loginUiState.update { it.copy(loginError = null, signUpError = null) }
    }

    // Make Account
    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateUserNameForm()) {
                throw IllegalArgumentException("User name cannot be empty.")
            }

            if (!validateSignUpForm()) {
                throw IllegalArgumentException("Email and password cannot be empty.")
            }
            _loginUiState.update { it.copy(isLoading = true) }

            if (loginUiState.value.passwordSignUp != loginUiState.value.confirmPasswordSignUp) {
                throw IllegalArgumentException("Password does not match.")
            }

            _loginUiState.update { it.copy(signUpError = null) }
            repository.createUser(
                userName = loginUiState.value.userNameSignUp,
                email = loginUiState.value.emailSignUp,
                password = loginUiState.value.passwordSignUp
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginUiState.update { it.copy(isSuccessLogin = true) }
                } else {
                    Toast.makeText(
                        context,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginUiState.update { it.copy(isSuccessLogin = false) }
                }
            }

        } catch (e: Exception) {
            _loginUiState.update { it.copy(signUpError = e.localizedMessage) }
            e.printStackTrace()
        } finally {
            _loginUiState.update { it.copy(isLoading = false) }
        }
    }


    // Email Login
    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("Email and password cannot be empty.")
            }
            _loginUiState.update { it.copy(isLoading = true) }
            _loginUiState.update { it.copy(loginError = null) }

            repository.login(
                email = loginUiState.value.email,
                password = loginUiState.value.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginUiState.update { it.copy(isSuccessLogin = true) }
                } else {
                    Toast.makeText(
                        context,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginUiState.update { it.copy(isSuccessLogin = false) }
                }
            }

        } catch (e: Exception) {
            _loginUiState.update { it.copy(loginError = e.localizedMessage) }
            e.printStackTrace()
        } finally {
            _loginUiState.update { it.copy(isLoading = false) }
        }
    }


    // Google Login
    fun googleLoginUser(context: Context, credential: AuthCredential) = viewModelScope.launch {
        try {
            _loginUiState.update { it.copy(isLoading = true) }
            _loginUiState.update { it.copy(loginError = null) }

            repository.googleLogin(
                credential
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginUiState.update { it.copy(isSuccessLogin = true) }
                } else {
                    Toast.makeText(
                        context,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    _loginUiState.update { it.copy(isSuccessLogin = false) }
                }
            }
        } catch (e: Exception) {
            _loginUiState.update { it.copy(loginError = e.localizedMessage) }
            e.printStackTrace()
        } finally {
            _loginUiState.update { it.copy(isLoading = false) }
        }
    }

    fun signOut() {
        repository.signOut()
    }
}


data class LoginUiState(
    // 이메일, 비밀 번호
    val email: String = "",
    val password: String = "",

    // 회원 가입 이름, 이메일, 비밀 번호, 2차 확인 비밀 번호
    val userNameSignUp: String = "",
    val emailSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",

    // 상태
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null
)