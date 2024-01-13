package com.sbcf.pillbox.utils.validation

typealias ValidatorFn = (String) -> String?

interface InputValidators {
    fun required(value: String): String?
    fun minLength(min: Int): ValidatorFn
}