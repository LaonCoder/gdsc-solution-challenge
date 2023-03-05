package com.gdsc.wildlives.pages.components


import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gdsc.wildlives.pages.CheckoutScreen
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.search.screen.SearchScreen
import com.gdsc.wildlives.ui.theme.colorPrimary

@Composable
fun Dashboard(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val sectionState = remember { mutableStateOf(DashboardSection.Search) }
    val navItems = DashboardSection.values().toList()

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
