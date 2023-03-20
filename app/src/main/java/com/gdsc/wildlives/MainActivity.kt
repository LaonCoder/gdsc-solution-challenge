package com.gdsc.wildlives

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gdsc.wildlives.component.CameraPermissionTextProvider
import com.gdsc.wildlives.component.PermissionDialog
import com.gdsc.wildlives.navigation.Navigation
import com.gdsc.wildlives.pages.dashboard.DashboradViewModel
import com.gdsc.wildlives.pages.detail.DetailViewModel
import com.gdsc.wildlives.pages.login.LoginViewModel
import com.gdsc.wildlives.pages.profile.ProfileViewModel
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.MainUiTheme
import com.gdsc.wildlives.ui.theme.colorPrimary

class MainActivity : ComponentActivity() {

    // Permissions
    private val permissionsToRequest = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            // ViewModels
            val mainViewModel: MainViewModel = viewModel()
            val loginViewModel: LoginViewModel = viewModel()
            val searchViewModel: SearchViewModel = viewModel()
            val detailViewModel: DetailViewModel = viewModel()
            val dashboardViewModel: DashboradViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel()

            // Permissions
            val dialogQueue = mainViewModel.visiblePermissionDialogQueue
            val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    mainViewModel.onPermissionResult(
                        permission = Manifest.permission.CAMERA,
                        isGranted = isGranted
                    )
                }
            )

            MainUiTheme() {
                MainApp(
                    loginViewModel = loginViewModel,
                    searchViewModel = searchViewModel,
                    detailViewModel = detailViewModel,
                    dashboardViewModel = dashboardViewModel,
                    profileViewModel = profileViewModel,
                    cameraPermissionResultLauncher = cameraPermissionResultLauncher
                )
            }

            dialogQueue
                .reversed()
                .forEach { permission ->
                    PermissionDialog(
                        permissionTextProvider = when (permission) {
                            Manifest.permission.CAMERA -> {
                                CameraPermissionTextProvider()
                            }
                            else -> return@forEach
                        },
                        isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                            permission
                        ),
                        onDismiss = mainViewModel::dismissDialog,
                        onOkClick = {
                            mainViewModel.dismissDialog()
                        },
                        onGoToAppSettingsClick = ::openAppSettings
                    )
                }
        }
    }

    @Composable
    fun MainApp(
        loginViewModel: LoginViewModel,
        searchViewModel: SearchViewModel,
        detailViewModel: DetailViewModel,
        dashboardViewModel: DashboradViewModel,
        profileViewModel: ProfileViewModel,
        cameraPermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        MainUiTheme {
            Surface(color = colorPrimary) {
                Navigation(
                    loginViewModel = loginViewModel,
                    searchViewModel = searchViewModel,
                    detailViewModel = detailViewModel,
                    dashboardViewModel = dashboardViewModel,
                    profileViewModel = profileViewModel,
                    cameraPermissionResultLauncher = cameraPermissionResultLauncher
                )
            }
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}