package com.sbcf.pillbox.features.medicationreminders.services

import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeek
import com.sbcf.pillbox.features.medicationreminders.data.DayOfWeekMask
import com.sbcf.pillbox.features.medicationreminders.models.TimestampInfo
import com.sbcf.pillbox.utils.Clock
import java.util.Calendar
import javax.inject.Inject

class ReminderTimestampCalculatorImpl @Inject constructor(private val clock: Clock) :
    ReminderTimestampCalculator {
    override fun getEarliestTimestamp(info: TimestampInfo): Long? {
        val now = clock.now()

        val nextDelivery = info.nextDeliveryTimestamp ?: Long.MIN_VALUE
        val nowMs = now.timeInMillis
        if (isInTheFuture(nowMs, nextDelivery)) {
            return info.nextDeliveryTimestamp
        }

        val possibleToday = now.clone() as Calendar
        possibleToday.set(Calendar.HOUR_OF_DAY, info.hour)
        possibleToday.set(Calendar.MINUTE, info.minute)
        possibleToday.set(Calendar.SECOND, 0)
        possibleToday.set(Calendar.MILLISECOND, 0)

        if (canBeRescheduledToday(nowMs, possibleToday, info.days)) {
            return possibleToday.timeInMillis
        }

        if (info.days.isNotEmpty()) {
            return getRepeatingNextDayTimestamp(info, now)
        }

        val tomorrow = now.clone() as Calendar
        tomorrow.set(Calendar.HOUR_OF_DAY, 0)
        tomorrow.set(Calendar.MINUTE, 0)
        tomorrow.set(Calendar.SECOND, 0)
        tomorrow.set(Calendar.MILLISECOND, 0)
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        tomorrow.set(Calendar.HOUR_OF_DAY, info.hour)
        tomorrow.set(Calendar.MINUTE, info.minute)

        return tomorrow.timeInMillis
    }

    override fun getEarliestRepeatingTimestamp(info: TimestampInfo): Long? {
        val now = clock.now()

        val nextDelivery = info.nextDeliveryTimestamp ?: return null
        val nowMs = now.timeInMillis
        if (isInTheFuture(nowMs, nextDelivery)) {
            return info.nextDeliveryTimestamp
        }

        if (info.days.isNotEmpty()) {
            return null
        }

        return getRepeatingNextDayTimestamp(info, now)
    }

    private fun getRepeatingNextDayTimestamp(info: TimestampInfo, now: Calendar): Long? {
        val currentDayOfWeek = DayOfWeek.fromCalendar(now)
        var nextDayOfWeek = currentDayOfWeek.getNextDay()
        var dayDiff = 1

        while (dayDiff < 7) {
            if (info.days.isSet(nextDayOfWeek)) {
                break
            }

            nextDayOfWeek = nextDayOfWeek.getNextDay()
            dayDiff++
        }

        if (dayDiff == 7) {
            return null
        }

        val nextTime = now.clone() as Calendar
        nextTime.add(Calendar.DAY_OF_MONTH, dayDiff)
        nextTime.set(Calendar.HOUR_OF_DAY, info.hour)
        nextTime.set(Calendar.MINUTE, info.minute)

        return nextTime.timeInMillis
    }

    companion object {
        fun isInTheFuture(nowMs: Long, nextDelivery: Long) = (nowMs <= nextDelivery)
        fun canBeRescheduledToday(
            nowMs: Long,
            possibleToday: Calendar,
            enabledDays: DayOfWeekMask
        ): Boolean {
            if (possibleToday.timeInMillis < nowMs && enabledDays.isEmpty()) {
                return false
            }

            val currentDay = DayOfWeek.fromCalendar(possibleToday)

            return enabledDays.isSet(currentDay)
        }
    }
}