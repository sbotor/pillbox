package com.sbcf.pillbox.utils

object Formatters {
    fun time(hour: Int, minute: Int): String {
        val fmtHour = hour.toString().padStart(2, '0')
        val fmtMinute = minute.toString().padStart(2, '0')

        return "$fmtHour:$fmtMinute"
    }
}