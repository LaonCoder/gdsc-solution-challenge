package com.gdsc.wildlives.pages.search.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc.wildlives.pages.search.SearchUiState
import com.gdsc.wildlives.pages.search.SearchViewModel

@Composable
fun PracBar(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }

    val iconAlpha by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 1f,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            elevation = 6.dp,
            shape = RoundedCornerShape(30.dp),
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.height(52.dp)
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Button"
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(52.dp)
                ,
                value = searchUiState?.searchKeyword ?: "",
                onValueChange = {
                    searchViewModel?.onSearchKeywordChange(it)
                    if (it.isNotEmpty()) {
                        isSearching = true
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.DarkGray,
                    cursorColor = Color.LightGray,
                    disabledTextColor = Color.LightGray,
                    errorCursorColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    errorLabelColor = Color.DarkGray,
                    leadingIconColor = Color.Gray,
                    disabledLabelColor = Color.LightGray,
                    disabledLeadingIconColor = Color.LightGray,
                    errorLeadingIconColor = Color.LightGray,
                    trailingIconColor = Color.Gray,
                    disabledTrailingIconColor = Color.LightGray,
                    errorTrailingIconColor = Color.Gray,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    placeholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                ),
                singleLine = true,
                placeholder = { Text("Search for new animals.") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Button"
                    )
                },
                trailingIcon = {
                    if (isSearching) {
                        IconButton(onClick = {
                            searchViewModel?.onSearchKeywordChange("")
                            isSearching = false
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Search Button",
                                modifier = Modifier
                                    .size(22.dp)
                                    .background(Color.LightGray, RoundedCornerShape(30.dp))
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PracbarPrev() {
    PracBar(
        searchViewModel = null,
        searchUiState = null
    )
}