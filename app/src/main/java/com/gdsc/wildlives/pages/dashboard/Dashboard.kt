package com.gdsc.wildlives.pages.dashboard


import android.Manifest
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gdsc.wildlives.data.animalClassificationList
import com.gdsc.wildlives.data.animalData
import com.gdsc.wildlives.ml.Sample
import com.gdsc.wildlives.navigation.Screen
import com.gdsc.wildlives.pages.EncyclopediaScreen
import com.gdsc.wildlives.pages.donate.DonateViewModel
import com.gdsc.wildlives.pages.donate.screen.DonateScreen
import com.gdsc.wildlives.pages.encyclopedia.EncyclopediaViewModel
import com.gdsc.wildlives.pages.profile.ProfileViewModel
import com.gdsc.wildlives.pages.profile.Screen.ProfileScreen
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.search.screen.SearchScreen
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.net.URLEncoder
import java.nio.ByteBuffer
import java.nio.ByteOrder

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Dashboard(
    navController: NavController,
    dashboardViewModel: DashboradViewModel,
    searchViewModel: SearchViewModel,
    profileViewModel: ProfileViewModel,
    encyclopediaViewModel: EncyclopediaViewModel,
    donateViewModel: DonateViewModel,
    cameraPermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    val dashboardUiState = dashboardViewModel?.dashboardUiState?.collectAsState()?.value

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val sectionState = rememberSaveable{ mutableStateOf(DashboardSection.Search) }
    val navItems = DashboardSection.values().toList()
    val context = LocalContext.current


    LaunchedEffect(key1 = dashboardUiState?.onPhotoTaken) {
        if (dashboardUiState?.onPhotoTaken != false) {
            val classifiedAnimal = animalData.find { it.name == dashboardUiState!!.classified }
            Log.d("Classified Animal", classifiedAnimal.toString())

            val passingAnimalData = classifiedAnimal?.copy(
                imageUrl = URLEncoder.encode(classifiedAnimal.imageUrl, "UTF-8"),
                photoTakenTime = dashboardUiState?.currentTime
            )

            val animalDataJson = Json.encodeToString(passingAnimalData)
            Log.d("animalJson", animalDataJson)

            navController.navigate(Screen.ClassificationScreen.route + "?animal=${animalDataJson}")

            dashboardViewModel?.resetState();
        }
    }


    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { originalBitmap ->
            var image = originalBitmap
            val imageSize = 224

            if (image != null) {
                val dimension = if (image!!.width < image!!.height) image.width else image.height  // Get Min
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)

                try {
                    val model = Sample.newInstance(context)  // Get ML model.

                    // Creates inputs for reference.
                    val inputFeature0: TensorBuffer =
                        TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                    val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
                    byteBuffer.order(ByteOrder.nativeOrder())

                    // Get 1D array of 224 * 224 pixels in image
                    val intValues = IntArray(imageSize * imageSize)
                    image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

                    // Iterate over pixels and extract R, G, and B values. Add to bytebuffer.
                    var pixel = 0
                    for (i in 0 until imageSize) {
                        for (j in 0 until imageSize) {
                            val `val` = intValues[pixel++] // RGB
                            byteBuffer.putFloat(((`val` shr 16) and 0xFF) * (1f / 255f))
                            byteBuffer.putFloat(((`val` shr 8) and 0xFF) * (1f / 255f))
                            byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                        }
                    }
                    inputFeature0.loadBuffer(byteBuffer)

                    // Runs model inference and gets result.
                    val outputs: Sample.Outputs = model.process(inputFeature0)
                    val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
                    val confidences: FloatArray = outputFeature0.floatArray

                    // Find the index of the class with the biggest confidence.
                    var maxPos = 0
                    var maxConfidence = 0f
                    for (i in confidences.indices) {
                        if (confidences[i] > maxConfidence) {
                            maxConfidence = confidences[i]
                            maxPos = i
                        }
                    }

                    // TODO : 샘플 모델 이외의 모델을 사용할 경우 아래 클래스 리스트 수정하기
                    val classified = animalClassificationList[maxPos]
                    Log.d("Classified", classified)

                    dashboardViewModel?.onClassifiedChanged(
                        onPhotoTaken = true,
                        currentTime = System.currentTimeMillis().toString(),
                        classified = classified,
                        bitmapImage = image
                    )

                    // Releases model resources if no longer used.
                    model.close()

                } catch (e: IOException) {
                    Log.d("Error", "Cannot take pictures.")
                }
            }
        }


    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            BottomAppBar (
                modifier = Modifier
                    .height(65.dp)
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                cutoutShape = CircleShape,
                backgroundColor = MaterialTheme.colors.background,
                elevation = 22.dp
            ) {
                BottomBar(
                    items = navItems,
                    currentSection = sectionState.value,
                    onSectionSelected = { sectionState.value = it },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    when(cameraPermissionState.status) {
                        PermissionStatus.Granted -> {
                            takePhotoFromCameraLauncher.launch()
                        }
                        is PermissionStatus.Denied -> {
                            // TODO : Permission 이후에 바로 카메라가 켜질 수 있게 수정 필요
                            cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                },
                contentColor = Color.White,
                backgroundColor = colorPrimary
            ) {
                Icon(
                    imageVector = Icons.Filled.Camera,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "camera button"
                )
            }
        },

    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(
            modifier = modifier,
            targetState = sectionState.value
        )
        { section ->
            when (section) {
                DashboardSection.Search -> SearchScreen(
                    navController = navController,
                    searchViewModel = searchViewModel
                )
                DashboardSection.Collect -> EncyclopediaScreen(
                    navController = navController,
                    encyclopediaViewModel = encyclopediaViewModel
                )
                DashboardSection.Donate -> DonateScreen(
                    navController = navController,
                    donateViewModel = donateViewModel
                )
                DashboardSection.Profile -> ProfileScreen(
                    navController = navController,
                    profileViewModel = profileViewModel
                )
                else -> {}
            }
        }
    }
}

@Composable
private fun BottomBar(
    items: List<DashboardSection>,
    currentSection: DashboardSection,
    onSectionSelected: (DashboardSection) -> Unit,
) {
    BottomNavigation(
        modifier = Modifier
            .height(65.dp),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.background)
    ) {
        items.forEach { section ->

            val selected = section == currentSection

            if (section.sectionName == "donate") {
                Spacer(modifier = Modifier.width((80.dp)))
            }

            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = section.iconVector,
                        modifier = Modifier.size(30.dp),
                        contentDescription = section.sectionName + " button"
                    )
                },
                selected = selected,
                unselectedContentColor = Color.Gray,
                selectedContentColor = colorPrimary,
                onClick = { onSectionSelected(section) },
                alwaysShowLabel = false
            )
        }
    }
}

private enum class DashboardSection(
    val sectionName: String,
    val iconVector: ImageVector,
) {
    Search("search", Icons.Default.Search),
    Collect("collect", Icons.Default.ViewModule),
    Donate("donate", Icons.Default.VolunteerActivism),
    Profile("profile", Icons.Default.Person),
}
