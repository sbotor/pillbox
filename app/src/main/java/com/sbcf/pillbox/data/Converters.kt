package com.sbcf.pillbox.data

import androidx.room.TypeConverter
import com.sbcf.pillbox.features.medicationreminders.data.ReminderDayMask

class Converters {

    @TypeConverter
    fun convertToNotificationDayMask(value: Int): ReminderDayMask = ReminderDayMask(value)

    @TypeConverter
    fun convertToInt(value: ReminderDayMask): Int = value.value
}