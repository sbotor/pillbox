package com.sbcf.pillbox.features.medications.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface Dosage {
    val amount: Int
    val unit: DosageUnit
    val interval: Int
    val intervalType: DosageTimeInterval
}

class DosageState : Dosage {
    override var amount by mutableIntStateOf(DEFAULT_AMOUNT)
    override var unit by mutableStateOf(DEFAULT_UNIT)
    override var interval by mutableIntStateOf(DEFAULT_INTERVAL)
    override var intervalType by mutableStateOf(DEFAULT_INTERVAL_TYPE)

    companion object {
        private const val DEFAULT_AMOUNT = 1
        private val DEFAULT_UNIT = DosageUnit.PILL
        private const val DEFAULT_INTERVAL = 1
        private val DEFAULT_INTERVAL_TYPE = DosageTimeInterval.DAY
    }

    fun reset(dosage: Dosage? = null) {
        if (dosage == null) {
            amount = DEFAULT_AMOUNT
            unit = DEFAULT_UNIT
            interval = DEFAULT_INTERVAL
            intervalType = DEFAULT_INTERVAL_TYPE
        } else {
            amount = dosage.amount
            unit = dosage.unit
            interval = dosage.interval
            intervalType = dosage.intervalType
        }
    }
}

enum class DosageUnit(val abbreviation: String) {
    PILL("tabl."),
    MILLILITER("ml");

    override fun toString(): String {
        return abbreviation
    }
}

enum class DosageTimeInterval(
    private val singular: String,
    private val plural1: String,
    private val plural2: String
) {
    DAY("dzień", "dni", "dni"),
    WEEK("tydzień", "tygodnie", "tygodni"),
    MONTH("miesiąc", "miesiące", "miesięcy");

    fun getName(interval: Int): String {
        return if (interval == 1)
            this.singular
        else if ((interval % 100 !in 5..21)
            && interval % 10 < 5
            && interval % 10 != 0
            && interval % 10 != 1)
            this.plural1
        else
            this.plural2
    }
}