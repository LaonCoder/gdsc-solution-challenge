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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gdsc.wildlives.ml.PracticeModel
import com.gdsc.wildlives.pages.CheckoutScreen
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.search.screen.SearchScreen
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Dashboard(
    navController: NavController,
    dashboardViewModel: DashboradViewModel,
    searchViewModel: SearchViewModel,
    cameraPermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    val dashboardUiState = dashboardViewModel?.dashboardUiState?.collectAsState()?.value

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val sectionState = remember { mutableStateOf(DashboardSection.Search) }
    val navItems = DashboardSection.values().toList()
    val context = LocalContext.current


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
                    val model = PracticeModel.newInstance(context)  // Get ML model.

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
                    val outputs: PracticeModel.Outputs = model.process(inputFeature0)
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

                    // Classes
                    val classes = arrayOf(
                        "Antelope", "Badger", "Bat", "Bear", "Bee",
                        "Beetle", "Bison", "Boar", "ButterFly", "Cat",
                        "Caterpillar", "Chimpanzee", "Cockroach", "Cow", "Coyote",
                        "Crab", "Crow", "Deer", "Dog", "Dolphin",
                        "Donkey", "Dragonfly", "Duck", "Eagle", "Elephant",
                        "Flamingo", "Fly", "Fox", "Goat", "GoldFish",
                        "Goose", "Gorilla", "Grasshopper", "Hamster", "Hare",
                        "Hedgehog", "Hippopotamus", "Hornbill", "Horse", "Hummingbird",
                        "Hyena", "Jellyfish", "Kangaroo", "Koala", "Ladybug",
                        "Leopard", "Lion", "Lizard", "Lobster", "Mosquito",
                        "Moth", "Mouse", "Octopus", "Okapi", "Orangutan",
                        "Otter", "Owl", "Ox", "Oyster", "Panda",
                        "Parrot", "Pelecaniformes", "Penguin", "Pig", "Pigeon",
                        "Porcupine", "Possum", "Raccoon", "Rat", "Reindeer",
                        "Rhinoceros", "Sandpiper", "Seahorse", "Seal", "Shark",
                        "Sheep", "Snake", "Sparrow", "Squid", "Squirrel",
                        "Starfish", "Swan", "Tiger", "Turkey", "Turtle",
                        "Whale", "Wolf", "Wombat", "Woodpecker","Zebra"
                    )

                    Log.d("Classified", classes[maxPos])
                    dashboardViewModel?.onClassifiedChanged(classified = classes[maxPos], bitmapImage = image)



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
                DashboardSection.Collect -> CheckoutScreen()
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

            if (section.sectionName == "trail") {
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
    Trail("trail", Icons.Default.Landscape),
    Profile("profile", Icons.Default.Person),
}
