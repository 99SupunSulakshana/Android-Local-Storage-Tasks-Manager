package com.example.todoapplication.ui.screens.fragments.displaytodo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapplication.model.Todo
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Draft_bg
import com.example.todoapplication.ui.theme.Green_1
import com.example.todoapplication.ui.theme.Pink80
import com.example.todoapplication.ui.theme.Teal200
import com.example.todoapplication.ui.theme.Ui_violet
import com.example.todoapplication.ui.theme.Yellow_1

@Composable
fun DisplayTodoScreen(
    todo: Todo
){
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DisplayDataItem(
                value = "${todo.name}",
                color = Yellow_1
            )
            DisplayDataItem(
                value = "${todo.desc}",
                color = Green_1
            )
            DisplayDataItem(
                value = "${todo.date}",
                color = Blue_1
            )
            DisplayDataItem(
                value = "${todo.time}",
                color = Ui_violet
            )
            DisplayDataItem(
                value = "${todo.reminder}",
                color = Draft_bg
            )
            DisplayDataItem(
                value = "${todo.priority}",
                color = Teal200
            )
            DisplayDataItem(
                value = "${todo.status}",
                color = Pink80
            )
        }
    }
}