package com.gdsc.wildlives.pages.search.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import com.gdsc.wildlives.ui.theme.ghost_white

@Composable
fun SearchBar(
    searchViewModel: SearchViewModel?,
    searchUiState: SearchUiState?
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            elevation = 6.dp,
            shape = RoundedCornerShape(30.dp),
        ) {
            if (!isExpanded) {
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Button",
                        tint = Color.Gray
                    )
                }
            } else {
                Box(
                    modifier = Modifier.size(52.dp).background(Color.White)
                    ,
                ) {
                    
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandHorizontally (
                    expandFrom = Alignment.End
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
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
                        textColor = if (isExpanded) Color.DarkGray else Color.Transparent,
                        cursorColor = if (isExpanded) Color.LightGray else Color.Transparent,
                        disabledTextColor = if (isExpanded) Color.LightGray else Color.Transparent,
                        errorCursorColor = if (isExpanded) Color.LightGray else Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        errorLabelColor = if (isExpanded) Color.DarkGray else Color.Transparent,
                        leadingIconColor = Color.Gray,
                        disabledLabelColor = Color.LightGray,
                        disabledLeadingIconColor = Color.LightGray,
                        errorLeadingIconColor = Color.LightGray,
                        trailingIconColor = Color.Gray,
                        disabledTrailingIconColor = Color.LightGray,
                        errorTrailingIconColor = Color.Gray,
                        focusedLabelColor = if (isExpanded) Color.LightGray else Color.Transparent,
                        unfocusedLabelColor = if (isExpanded) Color.LightGray else Color.Transparent,
                        placeholderColor = if (isExpanded) Color.Gray else Color.Transparent,
                        disabledPlaceholderColor = if (isExpanded) Color.Gray else Color.Transparent
                    ),
                    singleLine = true,
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        IconButton(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .background(ghost_white, RoundedCornerShape(30.dp))
                            ,
                            onClick = {
                                isExpanded = false
                                isSearching = false
                                searchViewModel?.onSearchKeywordChange("")
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = "Search Button"
                            )
                        }
                    },
                    trailingIcon = {
                        if (isSearching) {
                            IconButton(onClick = {
                                searchViewModel?.onSearchKeywordChange("")
                                isSearching = false
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove Icon",
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
}

@Preview(showBackground = true)
@Composable
fun SearchBarPrev() {
    SearchBar(
        searchViewModel = null,
        searchUiState = null
    )
}