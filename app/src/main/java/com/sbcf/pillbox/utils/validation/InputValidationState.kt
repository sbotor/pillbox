package com.sbcf.pillbox.utils.validation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class InputValidationState(private val validators: List<ValidatorFn>) {
    constructor(vararg validators: ValidatorFn) : this(validators.toList())

    private var isDirty = false

    var error: String? by mutableStateOf(null)
        private set
    var isValid by mutableStateOf(true)
        private set
    
    fun markAsDirty(value: Boolean = true) {
        isDirty = value
    }

    fun validate(value: String): Boolean {
        if (validators.isEmpty()) {
            return true
        }

        for (validator in validators) {
            val result = validator(value)
            if (!result.isNullOrEmpty()) {
                return updateState(false, result)
            }
        }

        return updateState(true, null)
    }

    fun reset() {
        markAsDirty(false)
        updateState(true, null)
    }

    private fun updateState(isValid: Boolean, error: String?): Boolean {
        this.isValid = isValid
        this.error = error

        return isValid
    }
}