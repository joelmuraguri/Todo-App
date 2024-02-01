package com.joel.todo

import android.app.Application
import com.joel.todo.di.AppContainer
import com.joel.todo.di.AppDataContainer

class TodoApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}