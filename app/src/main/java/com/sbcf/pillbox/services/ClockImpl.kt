package com.sbcf.pillbox.services

import java.util.Calendar

class ClockImpl : Clock {
    override fun now(): Calendar = Calendar.getInstance()
}