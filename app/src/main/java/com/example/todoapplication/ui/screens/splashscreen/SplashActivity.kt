package com.example.todoapplication.ui.screens.splashscreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.todoapplication.constant.Util
import com.example.todoapplication.constant.Util.formatTime
import com.example.todoapplication.ui.screens.mytodolistscreen.MyToDoListsActivity
import com.example.todoapplication.ui.screens.splashscreen.composables.SplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashVM by viewModels()
    private val isComplete: MutableState<Boolean> = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val complete by viewModel.complete.observeAsState(false)
            isComplete.value = complete
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                needPermission()
            }else{
                if(complete){
                    val intent = Intent(this@SplashActivity, MyToDoListsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(Color.White)
            systemUiController.setNavigationBarColor(Color.White)
            SplashScreen()
        }
    }

    private fun needPermission() {
        val permissionsArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableListOf(
                Manifest.permission.POST_NOTIFICATIONS,
            )
        } else {
            mutableListOf()
        }

        Dexter.withContext(this)
            .withPermissions(
                permissionsArray
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        val intent = Intent(this@SplashActivity, MyToDoListsActivity::class.java)
                        startActivity(intent)
                        finish()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:${applicationContext.packageName}")
                    startActivity(intent)
                }
            }).check()
    }
}

