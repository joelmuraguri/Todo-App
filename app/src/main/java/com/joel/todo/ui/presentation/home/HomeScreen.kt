package com.joel.todo.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.todo.data.Todo
import com.joel.todo.utils.AppEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate : (AppEvents.Navigate) -> Unit,
    homeViewModel: HomeViewModel
){

    val allTodos by homeViewModel.allTodos.collectAsState()

    LaunchedEffect(key1 = true){
        homeViewModel.uiEvents.collect { events ->
            when(events){
                is AppEvents.Navigate -> {
                    onNavigate(events)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                homeViewModel.onEvents(HomeEvents.AddNote)
            }) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(
                        text = "Add",
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { /*TODO*/ },
                actions = {
                    IconButton(onClick = {
                        homeViewModel.onEvents(HomeEvents.DeleteAll)
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            HomeContents(
                todos = allTodos,
                onItemClick = {
                    homeViewModel.onEvents(HomeEvents.OnNoteClick(it))
                }
            )
        }
    }
}

@Composable
fun HomeContents(
    todos : List<Todo>,
    onItemClick : (Todo) -> Unit
){

    LazyColumn(){
        items(todos){todo ->
            TodoItem(
                todo = todo,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onItemClick : (Todo) -> Unit
){

    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onItemClick(todo)
            }
            .fillMaxWidth(),

    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {
            Text(
                text = todo.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = todo.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}