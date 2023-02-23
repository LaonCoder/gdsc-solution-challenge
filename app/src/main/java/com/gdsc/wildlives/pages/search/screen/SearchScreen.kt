package com.gdsc.wildlives.pages.search.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.pages.search.component.SearchBar
import com.gdsc.wildlives.pages.search.component.SearchHeader
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
                .background(color = ghost_white)
        ) {
            ConstraintLayout {

                val (SearchViewRef, contentRef) = createRefs()

                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .constrainAs(SearchViewRef) {
                            top.linkTo(contentRef.top)
                            bottom.linkTo(contentRef.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                    SearchHeader(searchViewModel, searchUiState)
                }

                Surface(
                    color = ghost_white,
                    shape = RoundedCornerShape(40.dp).copy(
                        bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize
                    ),
                    modifier = Modifier
                        .height(800.dp)
                        .fillMaxSize()
                        .padding(top = 100.dp)
                        .constrainAs(contentRef) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text("Hello")

                }
            }
        }
    }
}


@Composable
@Preview
fun HomeScreenPrev() {
    SearchScreen(navController = rememberNavController(), searchViewModel = null)
}