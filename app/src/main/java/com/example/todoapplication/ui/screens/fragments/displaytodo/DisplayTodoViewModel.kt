package com.example.todoapplication.ui.screens.fragments.displaytodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.database.TodoDatabase
import com.example.todoapplication.model.Todo
import com.example.todoapplication.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DisplayTodoViewModel(
    application: Application
): AndroidViewModel(application) {
    val readAllData: LiveData<List<Todo>>

    private val repository: TodoRepository

    init {
        val userDao = TodoDatabase.getDatabase(application).todoDoa()
        repository = TodoRepository(userDao)
        readAllData = repository.readAllData
    }

    fun updateTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteUser(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

}