package com.sbcf.pillbox.utils.validation

import android.content.Context
import com.sbcf.pillbox.R
import dagger.hilt.android.qualifiers.ApplicationContext

class InputValidatorsImpl(private val context: Context) : InputValidators {
    override fun required(value: String): String? {
        if (value.isEmpty()) {
            return message(R.string.validation_required)
        }
        return null
    }

    override fun maxLength(max: Int): ValidatorFn {
        return fun(value: String): String? {
            if (value.length > max) {
                return message(R.string.validation_max_length, " $max")
            }
            return null
        }
    }

    override fun minLength(min: Int): ValidatorFn {
        return fun(value: String): String? {
            if (value.length < min) {
                return message(R.string.validation_min_length, " $min")
            }
            return null
        }
    }

    private fun message(id: Int, suffix: String? = null): String {
        val str = context.getString(id)
        if (suffix.isNullOrEmpty()) {
            return str
        }
        return str + suffix
    }
}