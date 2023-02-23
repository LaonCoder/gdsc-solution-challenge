package com.gdsc.wildlives.pages.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.wildlives.R
import com.gdsc.wildlives.pages.search.SearchUiState
import com.gdsc.wildlives.pages.search.SearchViewModel
import com.gdsc.wildlives.ui.theme.colorPrimary
import com.gdsc.wildlives.ui.theme.dark_gray

@Composable
fun SearchHeader(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?
) {
    Surface(
        color = colorPrimary
    ) {
        Box()
        {
            Image(
                painter = painterResource(id = R.drawable.search_header_image),
                contentDescription = "search_bg",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier.statusBarsPadding(),
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                SearchBar(
                    searchViewModel = searchViewModel,
                    searchUiState = searchUiState
                )

                Spacer(modifier = Modifier.weight(1f))

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
    }
}

@Composable
@Preview
fun SearchHeaderPrev() {
    SearchHeader(searchViewModel = null, searchUiState = null)
}