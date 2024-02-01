package com.joel.todo.domain.repo

import com.joel.todo.data.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getAllTodo() : Flow<List<Todo>>

    fun getTodoById(id : Int) : Flow<Todo>

    suspend fun insert(todo: Todo)

    suspend fun update(todo: Todo)

    suspend fun delete(todo: Todo)

    suspend fun deleteAll()
}