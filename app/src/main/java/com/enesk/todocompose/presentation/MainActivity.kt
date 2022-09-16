package com.enesk.todocompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.enesk.todocompose.presentation.navigation.SetupNavGraph
import com.enesk.todocompose.presentation.viewmodel.SharedViewModel
import com.enesk.todocompose.presentation.ui.theme.ToDoComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private lateinit var navController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}

