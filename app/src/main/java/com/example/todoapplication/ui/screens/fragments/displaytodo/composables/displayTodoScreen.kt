package com.example.todoapplication.ui.screens.fragments.displaytodo.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapplication.R
import com.example.todoapplication.model.Todo
import com.example.todoapplication.ui.screens.fragments.mylist.composables.isExpired
import com.example.todoapplication.ui.theme.Blue_1
import com.example.todoapplication.ui.theme.Draft_bg
import com.example.todoapplication.ui.theme.Green_1
import com.example.todoapplication.ui.theme.Pink80
import com.example.todoapplication.ui.theme.Teal200
import com.example.todoapplication.ui.theme.Ui_violet
import com.example.todoapplication.ui.theme.Yellow_1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayTodoScreen(
    todo: Todo
){
    val statusMaker = remember { mutableStateOf("") }
    if(todo.status != "COMPLETED"){
        if(isExpired(todo.date)){
            statusMaker.value = "EXPIRED"
        }else{
            statusMaker.value = todo.status
        }
    }else{
        statusMaker.value = todo.status
    }
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
            Image(
                painterResource(id = R.drawable.verified),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(height = 150.dp, width = 150.dp)
                    .fillMaxWidth()
                    .padding(top = 13.dp, end = 30.dp,start = 30.dp, bottom = 10.dp)
            )
            DisplayDataItem(
                value = todo.name,
                color = Yellow_1
            )
            DisplayDataItem(
                value = todo.desc,
                color = Green_1
            )
            DisplayDataItem(
                value = todo.date,
                color = Blue_1
            )
            DisplayDataItem(
                value = todo.time,
                color = Ui_violet
            )
            DisplayDataItem(
                value = todo.reminder,
                color = Draft_bg
            )
            DisplayDataItem(
                value = todo.priority,
                color = Teal200
            )
            DisplayDataItem(
                value = statusMaker.value,
                color = Pink80
            )
        }
    }
}