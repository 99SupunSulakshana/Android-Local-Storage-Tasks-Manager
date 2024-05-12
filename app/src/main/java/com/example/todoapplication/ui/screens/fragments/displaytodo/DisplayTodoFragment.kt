package com.example.todoapplication.ui.screens.fragments.displaytodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapplication.model.Todo
import com.example.todoapplication.ui.screens.fragments.displaytodo.composables.DisplayInfoBottomBar
import com.example.todoapplication.ui.screens.fragments.displaytodo.composables.DisplayTodoScreen
import com.example.todoapplication.ui.screens.fragments.displaytodo.composables.RemoveDialogBox
import com.example.todoapplication.ui.screens.fragments.displaytodo.composables.TopBarDisplayScreen
import com.example.todoapplication.ui.screens.fragments.mylist.composables.MyTodoListScreen
import com.example.todoapplication.ui.screens.fragments.mylist.composables.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class DisplayTodoFragment : Fragment() {

    private val viewModel: DisplayTodoViewModel by viewModels()
    val args: DisplayTodoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(Color.White)
                systemUiController.setNavigationBarColor(Color.White)

                val showRemoveDialog = remember { mutableStateOf(false) }
                if (showRemoveDialog.value)
                    RemoveDialogBox(
                        setShowDialog = { showRemoveDialog.value = it },
                        onClickRemove = {
                            viewModel.deleteUser(args.todo)
                            showRemoveDialog.value = false
                            Toast.makeText(
                                requireContext(),
                                "Successfully Added!",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                        },
                        onCancelClick = {
                            showRemoveDialog.value = false
                        }
                    )
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Scaffold (
                        topBar =  {
                            TopBarDisplayScreen(
                                onBackNav = {
                                    findNavController().popBackStack()
                                },
                                onInfoRemove = {
                                    showRemoveDialog.value = true
                                }
                            )
                        },
                        content = { innerPadding ->
                            Box(
                                modifier = Modifier.padding(innerPadding),){
                                DisplayTodoScreen(
                                    todo = args.todo
                                )
                            }
                        },
                        bottomBar = {
                            DisplayInfoBottomBar(
                                onClickSave = {
                                    Log.e("DisplayTodoFragment","name - ${args.todo.name}, desc - ${args.todo.desc}, date = ${args.todo.date}, time = ${args.todo.time}, reminder = ${args.todo.reminder}, priority = ${args.todo.priority}, status = ${args.todo.status}")
                                    val todo = Todo(
                                        id = args.todo.id,
                                        name = args.todo.name,
                                        desc = args.todo.desc,
                                        date = args.todo.date,
                                        time = args.todo.time,
                                        reminder = args.todo.reminder,
                                        priority = args.todo.priority,
                                        status = "COMPLETED"
                                    )
                                    viewModel.updateTodo(todo)
                                    Toast.makeText(
                                        requireContext(),
                                        "Successfully Updated!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    findNavController().popBackStack()
                                },
                                todoStatus = args.todo.status,
                                date = args.todo.date
                            )
                        }
                    )
                }
            }
        }
    }

}