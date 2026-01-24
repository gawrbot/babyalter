package dev.rhea.babyalter.data

import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

data class AgeResult(
    val remainingDaysAfterYears: Long,
    val daysTotal: Long,
    val weeks: Long,
    val remainingDaysAfterWeeks: Long,
    val months: Int,
    val remainingWeeksAfterMonths: Long,
    val remainingDaysAfterMonths: Long,
    val years: Int,
    val remainingMonthsAfterYears: Int,
    val remainingWeeksAfterYears: Long
)

fun calculateAge(birthDate: LocalDate, today: LocalDate): AgeResult {
    val daysTotal = ChronoUnit.DAYS.between(birthDate, today)
    val weeks = daysTotal / 7
    val remainingDaysAfterWeeks = daysTotal % 7

    val period = Period.between(birthDate, today)
    val years = period.years
    val monthsTotal = period.years * 12 + period.months

    // Rest Monate/Wochen/Tage nach Jahren
    val dateAfterYears = birthDate.plusYears(years.toLong())
    val periodAfterYears = Period.between(dateAfterYears, today)
    val remainingMonthsAfterYears = periodAfterYears.months
    val remainingDaysAfterMonthsInYear = ChronoUnit.DAYS.between(
        dateAfterYears.plusMonths(remainingMonthsAfterYears.toLong()),
        today
    )
    val remainingWeeksAfterYears = remainingDaysAfterMonthsInYear / 7
    val remainingDaysAfterYears = remainingDaysAfterMonthsInYear % 7

    // Rest Wochen/Tage nach Monaten (wie vorher)
    val dateAfterMonths = birthDate.plusMonths(monthsTotal.toLong())
    val remainingDaysAfterMonths = ChronoUnit.DAYS.between(dateAfterMonths, today)

    return AgeResult(
        daysTotal = daysTotal,
        weeks = weeks,
        remainingDaysAfterWeeks = remainingDaysAfterWeeks,
        months = monthsTotal,
        remainingWeeksAfterMonths = remainingDaysAfterMonths / 7,
        remainingDaysAfterMonths = remainingDaysAfterMonths % 7,
        years = years,
        remainingMonthsAfterYears = remainingMonthsAfterYears,
        remainingWeeksAfterYears = remainingWeeksAfterYears,
        remainingDaysAfterYears = remainingDaysAfterYears,
    )
}


