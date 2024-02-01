package com.joel.todo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joel.todo.data.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo_Table ORDER BY timeStamp DESC")
    fun getAllTodo() : Flow<List<Todo>>

    @Query("SELECT * FROM Todo_Table WHERE id=:id")
    fun getTodoById(id : Int) : Flow<Todo>

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("DELETE FROM Todo_Table")
    suspend fun deleteAll()
}