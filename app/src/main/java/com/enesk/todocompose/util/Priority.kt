package com.enesk.todocompose.util

import androidx.compose.ui.graphics.Color
import com.enesk.todocompose.presentation.ui.theme.HighPriorityColor
import com.enesk.todocompose.presentation.ui.theme.LowPriorityColor
import com.enesk.todocompose.presentation.ui.theme.MediumPriorityColor
import com.enesk.todocompose.presentation.ui.theme.NonePriorityColor

//The importance of the tasks will be determined by this class.
enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}