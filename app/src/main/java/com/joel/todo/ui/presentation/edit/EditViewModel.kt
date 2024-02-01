package com.joel.todo.ui.presentation.edit

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.todo.Screens
import com.joel.todo.data.Todo
import com.joel.todo.domain.repo.TodoRepository
import com.joel.todo.utils.AppEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private var _title = mutableStateOf("")
    val title : String
        get() = _title.value

    private var _id = mutableIntStateOf(0)
    val id : Int
        get() = _id.intValue

    private var _description = mutableStateOf("")
    val description : String
        get() =_description.value

    private val _uiEvents = Channel<AppEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _selectedTodo : MutableStateFlow<Todo?> = MutableStateFlow(null)
    val selectedTodo : StateFlow<Todo?> = _selectedTodo

    fun onEvents(events: EditEvents){
        when(events){
            EditEvents.NavBack -> {
                viewModelScope.launch {
                    _uiEvents.send(AppEvents.PopBackStack)
                }
            }
            is EditEvents.OnDescriptionChange -> {
                _description.value = events.description
            }
            is EditEvents.OnTitleChange -> {
                _title.value = events.title
            }
            EditEvents.Save -> {
                viewModelScope.launch {
                    val todo = Todo(
                        title = _title.value,
                        description = _description.value,
                        timeStamp = System.currentTimeMillis()
                    )
                    todoRepository.insert(todo)
                    _uiEvents.send(AppEvents.Navigate(Screens.Home.route))
                }
            }
            is EditEvents.UpdateTextFields -> {
                viewModelScope.launch {
                    if (events.todo != null){
                        _id.intValue = events.todo.id
                        _title.value = events.todo.title
                        _description.value = events.todo.description
                    } else {
                        _id.intValue = 0
                        _title.value = ""
                        _description.value = ""
                    }
                }
            }
            is EditEvents.GetSelectedTodo -> {
                viewModelScope.launch {
                    todoRepository.getTodoById(events.todoId).collect{ todo ->
                        _selectedTodo.value = todo
                    }
                }
            }

            is EditEvents.Delete -> {
                viewModelScope.launch {
                    val todo = Todo(
                        title = events.todo.title,
                        description = events.todo.description,
                    )
                    todoRepository.delete(todo)
                    _uiEvents.send(AppEvents.Navigate(Screens.Home.route))
                }
            }
            is EditEvents.Update -> {
                viewModelScope.launch {
                    val todo = Todo(
                        title = events.todo.title,
                        description = events.todo.description,
                    )
                    todoRepository.update(todo)
                    _uiEvents.send(AppEvents.Navigate(Screens.Home.route))
                }
            }
        }
    }
}