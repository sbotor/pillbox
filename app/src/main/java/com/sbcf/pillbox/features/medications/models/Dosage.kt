package com.sbcf.pillbox.features.medications.models

//I lost my sanity trying to make this a one-to-one relationship with medication and deal with all the repercussions
data class Dosage (
    var amount : Int,
    var unit : DosageUnit,
    var interval: Int,
    var intervalType: TimeInterval
)
{
    companion object
    {
        fun default() : Dosage
        {
            return Dosage( 1, DosageUnit.PILL, 1, TimeInterval.DAY)
        }
    }

    fun reset(state: Dosage = default())
    {
        this.amount = state.amount
        this.unit = state.unit
        this.interval = state.interval
        this.intervalType = state.intervalType
    }

    override fun toString(): String {
        return "$amount ${unit.shortcut} co $interval ${intervalType.getName(interval)}"
    }
}

enum class DosageUnit(val shortcut: String)
{
    PILL("tabl."),
    MILLILITER("ml");

    override fun toString(): String {
        return shortcut
    }
}

enum class TimeInterval(private val singular:String, private val plural1: String, private val plural2: String)
{
    DAY("dzień", "dni", "dni"),
    WEEK("tydzień", "tygodnie", "tygodni"),
    MONTH("miesiąc", "miesiące", "miesięcy");

    fun getName(interval: Int) : String
    {
        return if(interval == 1)
            this.singular
        else if((interval%100 !in 5..21) && interval%10 < 5 && interval%10 != 0 && interval%10 !=1)
            this.plural1
        else
            this.plural2
    }
}