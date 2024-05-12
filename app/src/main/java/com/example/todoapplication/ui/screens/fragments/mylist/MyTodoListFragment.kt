package com.example.todoapplication.ui.screens.fragments.mylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.model.Todo
import com.example.todoapplication.ui.screens.fragments.addtodo.AddTodoViewModel
import com.example.todoapplication.ui.screens.fragments.mylist.composables.CircularFloatingActionButton
import com.example.todoapplication.ui.screens.fragments.mylist.composables.MyTodoListScreen
import com.example.todoapplication.ui.screens.fragments.mylist.composables.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MyTodoListFragment : Fragment() {

    private val viewModel: MyTodoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val todoList by viewModel.getTodoList.observeAsState()
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(Color.White)
                systemUiController.setNavigationBarColor(Color.White)
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Scaffold (
                        topBar =  {
                            TopAppBar()
                        },
                        content = { innerPadding ->
                            Box(
                                modifier = Modifier.padding(innerPadding),){
                                todoList?.let { it1->
                                    MyTodoListScreen(
                                        onItemClick = {
                                            val todo = MyTodoListFragmentDirections.actionMyTodoListFragmentToDisplayTodoFragment(it)
                                            findNavController().navigate(todo)
                                        },
                                        onClickFilter = {

                                        },
                                        todoList = it1
                                    )
                                }
                            }
                        },
                        bottomBar = {},
                        floatingActionButton = {
                            CircularFloatingActionButton(
                                onClick = {
                                    findNavController().navigate(R.id.action_myTodoListFragment_to_addTodoFragment)
                                }
                            )
                        }
                    )
                }
            }
        }
    }

}