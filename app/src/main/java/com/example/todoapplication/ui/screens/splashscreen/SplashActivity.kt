package com.example.todoapplication.ui.screens.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import com.example.todoapplication.constant.Util
import com.example.todoapplication.constant.Util.formatTime
import com.example.todoapplication.ui.screens.mytodolistscreen.MyToDoListsActivity
import com.example.todoapplication.ui.screens.splashscreen.composables.SplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val complete by viewModel.complete.observeAsState(false)
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(Color.White)
            systemUiController.setNavigationBarColor(Color.White)
            SplashScreen()
            if(complete){
                val intent = Intent(this@SplashActivity, MyToDoListsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}