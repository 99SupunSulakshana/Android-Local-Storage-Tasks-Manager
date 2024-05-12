package com.example.todoapplication.ui.screens.fragments.displaytodo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.ui.theme.Gray_600
import com.example.todoapplication.ui.theme.firaSansNormal

@Composable
fun DisplayDataItem(
    value: String,
    color: Color
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            .background(
                color = Color.White, shape = RoundedCornerShape(15.dp)
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = color.copy(
                alpha = 0.2f
            )
        ) {
            Row(
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    fontFamily = firaSansNormal,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 0.dp)
                )
            }
        }
    }
}