package com.sbcf.pillbox.features.medicationreminders.services

import com.sbcf.pillbox.features.medicationreminders.models.TimestampInfo

interface ReminderTimestampCalculator {
    fun getEarliestTimestamp(info: TimestampInfo): Long?
    fun getEarliestRepeatingTimestamp(info: TimestampInfo): Long?
}