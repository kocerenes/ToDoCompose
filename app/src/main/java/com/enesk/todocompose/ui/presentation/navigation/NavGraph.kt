package com.enesk.todocompose.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.enesk.todocompose.ui.presentation.navigation.destinations.listComposable
import com.enesk.todocompose.ui.presentation.navigation.destinations.taskComposable
import com.enesk.todocompose.util.Constants.LIST_SCREEN

@Composable
fun SetupNavGraph(
    navController:NavHostController
) {
    val screen = remember(navController){
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ){
        listComposable(screen.task)
        taskComposable(screen.list)
    }
}