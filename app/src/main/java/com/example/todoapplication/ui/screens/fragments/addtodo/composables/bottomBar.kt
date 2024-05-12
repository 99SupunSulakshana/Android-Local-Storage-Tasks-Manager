package com.example.todoapplication.ui.screens.fragments.addtodo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.firaSansSemiBold

@Composable
fun BottomBar(
    onClickSave: ()->Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth().background(Color.White)
    ) {
        Button(
            onClick = {onClickSave()},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Blue_1
            ),
            modifier = Modifier
                .padding(
                    bottom = 10.dp, top = 20.dp, end = 16.dp, start = 16.dp
                )
                .weight(1f)
                .fillMaxWidth(),
            elevation = null
        ) {
            Text(
                text = "save",
                style = TextStyle(
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    fontFamily = firaSansSemiBold,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }

}