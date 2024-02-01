package com.joel.todo.utils

sealed class AppEvents {
    data class Navigate(val route : String) : AppEvents()
    data object PopBackStack : AppEvents()
}