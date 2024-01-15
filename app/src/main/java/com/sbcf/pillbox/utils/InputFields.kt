package com.sbcf.pillbox.utils

import com.sbcf.pillbox.utils.validation.InputState
import com.sbcf.pillbox.utils.validation.InputValidationState
import com.sbcf.pillbox.utils.validation.ValidatorFn

class InputFields {
    private val inputs = ArrayList<InputState>()

    fun create(vararg validators: ValidatorFn): InputState {
        val validation = InputValidationState(validators.toList())
        val state = InputState(validation)

        inputs.add(state)

        return state
    }

    fun validate(): Boolean {
        var isValid = true

        for (input in inputs) {
            val result = input.validation.validate(input.value)
            isValid = isValid && result
        }

        return isValid
    }
}