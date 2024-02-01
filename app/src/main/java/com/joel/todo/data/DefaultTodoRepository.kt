package com.joel.todo.data

import com.joel.todo.data.db.TodoDao
import com.joel.todo.domain.repo.TodoRepository
import kotlinx.coroutines.flow.Flow

class DefaultTodoRepository(
    private val todoDao: TodoDao
) : TodoRepository  {

    override fun getAllTodo(): Flow<List<Todo>> {
        return todoDao.getAllTodo()
    }

    override fun getTodoById(id: Int): Flow<Todo> {
        return todoDao.getTodoById(id)
    }

    override suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    override suspend fun update(todo: Todo) {
        todoDao.update(todo)
    }

    override suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }

    override suspend fun deleteAll() {
       todoDao.deleteAll()
    }

}