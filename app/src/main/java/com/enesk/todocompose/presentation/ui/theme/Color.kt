package com.enesk.todocompose.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

//App
val AppColor = Color(0xFF70A6D1)
val DarkAppColor = Color(0xFF106891)
val TopBarContentColor = Color(0xFFE4EBEB)

//Priority
val HighPriorityColor = Color(0xFFFF4646)
val MediumPriorityColor = Color(0xFFFFC114)
val LowPriorityColor = Color(0xFF00C980)
val NonePriorityColor = Color(0xFF999898)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9c)
val DarkGray = Color(0xFF141414)

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) AppColor else DarkAppColor

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.AlertDialogOutlineButtonsColor: Color
    @Composable
    get() = if (isLight) AppColor else LightGray

val Colors.AlertDialogButtonsColor: Color
    @Composable
    get() = if (isLight) AppColor else DarkGray

val Colors.AlertDialogButtonTextColor: Color
    @Composable
    get() = if (isLight) Color.Black else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) AppColor else Color.Black

