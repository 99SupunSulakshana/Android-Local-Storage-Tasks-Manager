package com.example.todoapplication.ui.screens.mytodolistscreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.fragment.NavHostFragment
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ActivityMyToDoListsBinding
import com.example.todoapplication.ui.screens.fragments.mylist.composables.MyTodoListScreen
import com.example.todoapplication.ui.screens.fragments.mylist.composables.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MyToDoListsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyToDoListsBinding

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyToDoListsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_todo) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.todo_list_nav_graph)
        graph.setStartDestination(R.id.myTodoListFragment)
        navHostFragment.navController.graph = graph
    }
}