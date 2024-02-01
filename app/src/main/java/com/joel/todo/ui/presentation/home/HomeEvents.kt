package com.joel.todo.ui.presentation.home

import com.joel.todo.data.Todo

sealed class HomeEvents {
    data object AddNote : HomeEvents()
    data class OnNoteClick(val todo: Todo) : HomeEvents()
    data object DeleteAll : HomeEvents()
}