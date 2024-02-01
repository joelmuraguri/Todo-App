package com.joel.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joel.todo.di.ViewModelFactory
import com.joel.todo.ui.presentation.edit.EditEvents
import com.joel.todo.ui.presentation.edit.EditScreen
import com.joel.todo.ui.presentation.edit.EditViewModel
import com.joel.todo.ui.presentation.home.HomeScreen
import com.joel.todo.ui.presentation.home.HomeViewModel
import com.joel.todo.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp()
                }
            }
        }
    }
}

@Composable
fun TodoApp(
    editViewModel: EditViewModel = viewModel(factory = ViewModelFactory.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = ViewModelFactory.Factory)
){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ){
        composable(Screens.Home.route){
            HomeScreen(
                onNavigate = { events ->
                    navController.navigate(events.route) },
                homeViewModel = homeViewModel
            )
        }
        composable(
            Screens.Edit.route + "?todoId={${TODO_NAV_ARGUMENT}}",
            arguments = listOf(navArgument(TODO_NAV_ARGUMENT){
                type = NavType.IntType
                defaultValue = -1
            })
        ){ navBackStackEntry ->

            val todoId = navBackStackEntry.arguments!!.getInt(TODO_NAV_ARGUMENT)
            LaunchedEffect(key1 = todoId){
                editViewModel.onEvents(EditEvents.GetSelectedTodo(todoId))
            }

            val selectedTodo by editViewModel.selectedTodo.collectAsState()
            LaunchedEffect(key1 = selectedTodo){
                editViewModel.onEvents(EditEvents.UpdateTextFields(selectedTodo))
            }
            EditScreen(
                popBackStack = {
                    navController.popBackStack()
                },
                onNavigate = { events ->
                    navController.navigate(events.route)
                },
                editViewModel = editViewModel,
                todo = selectedTodo
            )
        }
    }
}

const val TODO_NAV_ARGUMENT = "todoId"
sealed class Screens(val route : String){

    data object Home : Screens("home_route")
    data object Edit : Screens("edit_route")

}