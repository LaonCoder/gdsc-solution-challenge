package com.gdsc.wildlives.pages.search.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gdsc.wildlives.R
import com.gdsc.wildlives.data.AnimalClass
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.search.component.SearchBar
import com.gdsc.wildlives.pages.search.component.SearchSlideSection
import com.gdsc.wildlives.ui.theme.*

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel?
) {
    val searchUiState = searchViewModel?.searchUiState?.collectAsState()?.value
    val context = LocalContext.current

    SearchUiTheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = ghost_white),
        ) {
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                Image(
                    painter = painterResource(id = R.drawable.search_header_image),
                    contentDescription = "search_bg",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(-1f)
                )
                Column(
                    modifier = Modifier.statusBarsPadding(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.height(160.dp))

                    Column(
                        modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)
                    ) {
                        Text(
                            text = "Discover",
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Text(
                            text = "new wild animals",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                    }
                }
            }

            SearchSlideSection(
                searchViewModel = searchViewModel,
                searchUiState = searchUiState,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            SearchBar(
                searchViewModel = searchViewModel,
                searchUiState = searchUiState,
            )
        }
    }
}


@Composable
@Preview
fun HomeScreenPrev() {
    SearchScreen(
        navController = rememberNavController(),
        searchViewModel = null
    )
}