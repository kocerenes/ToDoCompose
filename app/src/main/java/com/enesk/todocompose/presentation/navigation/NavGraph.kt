package com.enesk.todocompose.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.enesk.todocompose.presentation.navigation.destinations.listComposable
import com.enesk.todocompose.presentation.navigation.destinations.taskComposable
import com.enesk.todocompose.presentation.viewmodel.SharedViewModel
import com.enesk.todocompose.util.Constants.LIST_SCREEN

@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController:NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController){
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ){
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
    }
}