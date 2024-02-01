package com.joel.todo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joel.todo.data.Todo

@Database(
    entities = [Todo::class],
    exportSchema = false,
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDoa() : TodoDao

    companion object{

        private var INSTANCE : TodoDatabase ?= null

        fun getDatabaseInstance(context: Context) : TodoDatabase{
            return  INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    TodoDatabase::class.java,
                    "Todo_Database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }

            }
        }
    }
}