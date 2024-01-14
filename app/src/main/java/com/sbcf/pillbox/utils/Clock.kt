package com.sbcf.pillbox.utils

import java.util.Calendar

interface Clock {
    fun now(): Calendar

    fun nowTruncateSeconds(): Calendar {
        val cal = now()
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)

        return cal
    }

    fun today(hour: Int = 0, minute: Int = 0): Calendar {
        val cal = nowTruncateSeconds()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)

        return cal
    }
}