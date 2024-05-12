package com.example.todoapplication.repository

import androidx.lifecycle.LiveData
import com.example.todoapplication.dao.TodoDao
import com.example.todoapplication.model.Todo

class TodoRepository(private val todoDao: TodoDao) {
    val readAllData: LiveData<List<Todo>> = todoDao.readAllData()

    suspend fun addTodo(todo: Todo){
        todoDao.addTodo(todo)
    }

    suspend fun updateTodo(todo: Todo){
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }

    suspend fun deleteAllTodos(){
        todoDao.deleteAllTodos()
    }

    fun searchTodo(searchQuery: String):LiveData<List<Todo>>{
        return todoDao.searchDatabase(searchQuery)
    }
}