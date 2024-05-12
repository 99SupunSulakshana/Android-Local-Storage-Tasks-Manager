package com.example.todoapplication.ui.screens.fragments.mylist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Gray_200
import com.example.todoapplication.ui.theme.darkerGrotesqueBold

@Composable
fun TopAppBar(){
    androidx.compose.material.TopAppBar(
        content = {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Welcome to ",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = darkerGrotesqueBold,
                        color = Gray_200
                    ),
                    modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 5.dp),
                    textAlign = TextAlign.Start
                )
                Text(
                    "Todo",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = darkerGrotesqueBold,
                        color = Blue_1
                    ),
                    modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 5.dp),
                    textAlign = TextAlign.Start
                )
                Text(
                    " App",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = darkerGrotesqueBold,
                        color = Gray_200
                    ),
                    modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 5.dp),
                    textAlign = TextAlign.Start
                )
            }
        },
        backgroundColor = Color.White,
        contentColor = Color.White,
        elevation = 0.dp
    )
}