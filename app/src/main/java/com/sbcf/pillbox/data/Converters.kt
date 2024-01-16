package com.sbcf.pillbox.data

import androidx.room.TypeConverter
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeekMask

class Converters {

    @TypeConverter
    fun convertToDaOfWeekMask(value: Int): DayOfWeekMask = DayOfWeekMask(value)

    @TypeConverter
    fun convertToInt(value: DayOfWeekMask): Int = value.value
}