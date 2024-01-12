package com.sbcf.pillbox.utils.validation

typealias ValidatorFn = (String) -> String?

interface InputValidators {
    fun required(value: String): String?
    fun maxLength(max: Int): ValidatorFn
    fun minLength(min: Int): ValidatorFn

    fun length(min: Int, max: Int): ValidatorFn {
        return fun(value: String): String? {
            var validator = minLength(min)
            var error = validator(value)

            if (!error.isNullOrEmpty()) {
                return error
            }

            validator = maxLength(max)
            error = validator(value)

            return error
        }
    }
}