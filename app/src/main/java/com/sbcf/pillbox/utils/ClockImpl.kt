package com.sbcf.pillbox.utils

import java.util.Calendar

class ClockImpl : Clock {
    override fun now(): Calendar {
        return Calendar.getInstance()
    }

    override fun fromTimestamp(timestamp: Long): Calendar {
        val cal = now()
        cal.timeInMillis = timestamp
        return cal
    }
}