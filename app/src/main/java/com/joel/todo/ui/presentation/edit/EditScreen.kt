package com.joel.todo.ui.presentation.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joel.todo.data.Todo
import com.joel.todo.utils.AppEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    popBackStack : () -> Unit,
    onNavigate : (AppEvents.Navigate) -> Unit,
    editViewModel: EditViewModel,
    todo: Todo?
){


    LaunchedEffect(key1 = true){
        editViewModel.uiEvents.collect{ events ->
            when(events){
                is AppEvents.Navigate -> {
                    onNavigate(events)
                }
                AppEvents.PopBackStack -> {
                    popBackStack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            if (todo != null){
                TopAppBar(
                    title = { /*TODO*/ },
                    navigationIcon = {
                        IconButton(onClick = {
                            popBackStack()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { editViewModel.onEvents(EditEvents.Delete(todo)) }) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                        }

                        IconButton(onClick = { editViewModel.onEvents(EditEvents.Update(todo)) }) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                        }
                    }
                )
            } else {
                TopAppBar(
                    title = { /*TODO*/ },
                    navigationIcon = {
                        IconButton(onClick = { popBackStack() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { editViewModel.onEvents(EditEvents.Save) }) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            EditScreenContent(
                title = editViewModel.title,
                description = editViewModel.description,
                onTitleChange = {
                    editViewModel.onEvents(EditEvents.OnTitleChange(it))
                },
                onDescriptionChange = {
                    editViewModel.onEvents(EditEvents.OnDescriptionChange(it))
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            )
        }
    }
}

@Composable
fun EditScreenContent(
    title : String,
    description : String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {

        TextFieldComposable(
            value = title,
            onValueChange = onTitleChange,
            maxLines = 1,
            label = "Title"
        )

        TextFieldComposable(
            value = description,
            onValueChange = onDescriptionChange,
            maxLines = 3,
            label = "Description"
        )

    }
}


@Composable
fun TextFieldComposable(
    value : String,
    onValueChange : (String) -> Unit,
    maxLines : Int,
    label : String,
){

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = maxLines,
        label = {
            Text(text = label)
        },
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxWidth(),

    )
}