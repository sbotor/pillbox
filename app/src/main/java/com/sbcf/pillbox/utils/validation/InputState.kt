package com.sbcf.pillbox.utils.validation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class InputState(val validation: InputValidationState) {
    constructor() : this(InputValidationState())

    var value by mutableStateOf("")

    fun reset(newValue: String) {
        value = newValue
        validation.reset()
    }
}