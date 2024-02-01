package com.joel.todo.ui.presentation.edit

import com.joel.todo.data.Todo

sealed class EditEvents {

    data class OnTitleChange(val title : String) : EditEvents()
    data class OnDescriptionChange(val description : String) : EditEvents()
    data object Save : EditEvents()
    data class Update(val todo: Todo) : EditEvents()
    data class UpdateTextFields(val todo: Todo?) : EditEvents()
    data class Delete(val todo: Todo) : EditEvents()
    data object NavBack : EditEvents()
    data class GetSelectedTodo(val todoId : Int) : EditEvents()
}