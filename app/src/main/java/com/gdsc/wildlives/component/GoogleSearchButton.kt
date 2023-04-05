package com.gdsc.wildlives.pages.detail.component

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.wildlives.R


@Composable
fun GoogleSearchButton(
    searchText: String
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Button(
        modifier = Modifier
            .offset(y = -(20.dp))
            .fillMaxWidth()
        ,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = {
            val searchUri = "https://www.google.com/search?q=$searchText"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUri))
            launcher.launch(intent)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Login Icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = "Search On Google",
                color = Color.DarkGray,
                fontSize = 18.sp
            )
        }
    }
}