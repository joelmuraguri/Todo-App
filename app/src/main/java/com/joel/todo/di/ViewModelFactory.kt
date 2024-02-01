package com.joel.todo.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.joel.todo.TodoApplication
import com.joel.todo.ui.presentation.edit.EditViewModel
import com.joel.todo.ui.presentation.home.HomeViewModel

object ViewModelFactory {

    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                todoApplication().container.todoRepository
            )
        }
        initializer {
            EditViewModel(
                todoApplication().container.todoRepository
            )
        }
    }
}


fun CreationExtras.todoApplication() : TodoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TodoApplication)