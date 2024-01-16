package com.sbcf.pillbox.utils

import java.util.Calendar

interface Clock {
    fun now(): Calendar
    fun fromTimestamp(timestamp: Long): Calendar
}