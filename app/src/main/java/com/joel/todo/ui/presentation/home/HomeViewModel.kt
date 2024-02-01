package com.joel.todo.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.todo.Screens
import com.joel.todo.data.Todo
import com.joel.todo.domain.repo.TodoRepository
import com.joel.todo.utils.AppEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _uiEvents = Channel<AppEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _allTodos = MutableStateFlow<List<Todo>>(emptyList())
    val allTodos : StateFlow<List<Todo>> = _allTodos

    init {
        getAllTodos()
    }

    fun onEvents(events: HomeEvents){
        when(events){
            HomeEvents.AddNote -> {
                viewModelScope.launch {
                    _uiEvents.send(AppEvents.Navigate(Screens.Edit.route))
                }
            }
            HomeEvents.DeleteAll -> {
                viewModelScope.launch {
                    todoRepository.deleteAll()
                }
            }
            is HomeEvents.OnNoteClick -> {
                viewModelScope.launch {
                    _uiEvents.send(AppEvents.Navigate(Screens.Edit.route + "?todoId=${events.todo.id}"))
                }
            }
        }
    }

    private fun getAllTodos(){
        viewModelScope.launch {
            todoRepository.getAllTodo().collect{ todos ->
                _allTodos.value = todos
            }
        }
    }
}