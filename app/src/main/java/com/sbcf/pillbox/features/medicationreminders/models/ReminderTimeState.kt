package com.sbcf.pillbox.features.medicationreminders.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sbcf.pillbox.utils.DisplayFormatter

class ReminderTimeState(private val formatter: DisplayFormatter) {
    var hour by mutableIntStateOf(0)
    var minute by mutableIntStateOf(0)
    var showTimePicker by mutableStateOf(false)

    override fun toString(): String = formatter.time(hour, minute)
}