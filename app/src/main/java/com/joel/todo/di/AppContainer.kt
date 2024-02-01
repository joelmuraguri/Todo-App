package com.joel.todo.di

import android.content.Context
import com.joel.todo.data.DefaultTodoRepository
import com.joel.todo.data.db.TodoDatabase
import com.joel.todo.domain.repo.TodoRepository

interface AppContainer {
    val todoRepository : TodoRepository
}

class AppDataContainer(
    private val context: Context
) : AppContainer{

    override val todoRepository: TodoRepository by lazy {
        DefaultTodoRepository(TodoDatabase.getDatabaseInstance(context).todoDoa())
    }

}