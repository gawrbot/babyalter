package dev.rhea.babyalter.data

import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

data class AgeResult(
    val daysTotal: Long,
    val weeks: Long,
    val remainingDaysAfterWeeks: Long,
    val months: Int,
    val remainingWeeksAfterMonths: Long,
    val remainingDaysAfterMonths: Long
)

fun calculateAge(birthDate: LocalDate, today: LocalDate): AgeResult {
    val daysTotal = ChronoUnit.DAYS.between(birthDate, today)

    val weeks = daysTotal / 7
    val remainingDaysAfterWeeks = daysTotal % 7

    val period = Period.between(birthDate, today)
    val monthsTotal = period.years * 12 + period.months

    val dateAfterMonths = birthDate.plusMonths(monthsTotal.toLong())
    val remainingDaysAfterMonths = ChronoUnit.DAYS.between(dateAfterMonths, today)

    return AgeResult(
        daysTotal = daysTotal,
        weeks = weeks,
        remainingDaysAfterWeeks = remainingDaysAfterWeeks,
        months = monthsTotal,
        remainingWeeksAfterMonths = remainingDaysAfterMonths / 7,
        remainingDaysAfterMonths = remainingDaysAfterMonths % 7
    )
}
