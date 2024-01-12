package com.sbcf.pillbox.utils.validation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class InputState(val validation: InputValidationState) {
    var value by mutableStateOf("")
}