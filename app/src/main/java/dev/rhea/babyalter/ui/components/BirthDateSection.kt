package dev.rhea.babyalter.ui.components

import android.app.DatePickerDialog
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.rhea.babyalter.data.calculateAge
import dev.rhea.babyalter.dataStore
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

data class NumberUnit(val value: Int, val singular: String, val plural: String)

@Composable
fun NumberUnitLine(
    parts: List<NumberUnit>,
    modifier: Modifier = Modifier
) {
    // AnnotatedString nur rebuilden, wenn sich parts ändern
    val text = remember(parts) {
        buildAnnotatedString {
            parts.forEachIndexed { index, part ->
                withStyle(SpanStyle(fontSize = 18.sp)) {
                    append(part.value.toString())
                }
                append(" ")
                withStyle(SpanStyle(fontSize = 12.sp, color = Color.Gray)) {
                    append(pluralize(part.value, part.singular, part.plural))
                }
                if (index != parts.lastIndex) append(" ")
            }
        }
    }

    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun BirthDateSection(
    title: String,
    date: LocalDate?,
    prefKey: Preferences.Key<String>,
    activity: ComponentActivity
) {
    val scope = rememberCoroutineScope()
    val today = remember { LocalDate.now() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(title, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(12.dp))

        if (date == null) {
            Button(onClick = {
                showDatePicker(activity, today) { selectedDate ->
                    scope.launch {
                        activity.dataStore.edit { prefs ->
                            prefs[prefKey] = selectedDate.toString()
                        }
                    }
                }
            }) {
                Text("Datum auswählen", fontSize = 18.sp)
            }
        } else {
            // Berechne Alter einmal
            val age = remember(date) { calculateAge(date, today) }

            // Formatiertes Datum einmal merken
            val formattedDate = remember(date) {
                date.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(Locale.getDefault())
                )
            }

            // Gesamt-Tage
            Text(text =
                "${age.daysTotal.toInt()} ${pluralize(age.daysTotal.toInt(), "Tag", "Tage")} ",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(), color = Color.Gray)

            Spacer(Modifier.height(6.dp))

            // Wochen + Resttage
            NumberUnitLine(
                parts = listOf(
                    NumberUnit(age.weeks.toInt(), "Woche", "Wochen"),
                    NumberUnit(age.remainingDaysAfterWeeks.toInt(), "Tag", "Tage")
                )
            )

            Spacer(Modifier.height(6.dp))

            // Monate + Restwochen + Resttage
            NumberUnitLine(
                parts = listOf(
                    NumberUnit(age.months, "Monat", "Monate"),
                    NumberUnit(age.remainingWeeksAfterMonths.toInt(), "Woche", "Wochen"),
                    NumberUnit(age.remainingDaysAfterMonths.toInt(), "Tag", "Tage")
                )
            )

            Spacer(Modifier.height(6.dp))

            // Jahre + Restmonate + Restwochen + Resttage
            Text(
                text =
                    "${age.years} ${pluralize(age.years, "Jahr", "Jahre")} " +
                            "${age.remainingMonthsAfterYears} ${pluralize(age.remainingMonthsAfterYears, "Monat", "Monate")} " +
                            "${age.remainingWeeksAfterYears.toInt()} ${pluralize(age.remainingWeeksAfterYears.toInt(), "Woche", "Wochen")} " +
                            "${age.remainingDaysAfterYears.toInt()} ${pluralize(age.remainingDaysAfterYears.toInt(), "Tag", "Tage")}",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(), color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(formattedDate, fontSize = 14.sp)

                Spacer(modifier = Modifier.width(18.dp))

                OutlinedButton(onClick = {
                    showDatePicker(activity, today) { selectedDate ->
                        scope.launch {
                            activity.dataStore.edit { prefs ->
                                prefs[prefKey] = selectedDate.toString()
                            }
                        }
                    }
                }) {
                    Text("Ändern")
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(onClick = {
                    scope.launch {
                        activity.dataStore.edit { prefs ->
                            prefs.remove(prefKey)
                        }
                    }
                }) {
                    Text("Löschen")
                }
            }
        }
    }
}

fun showDatePicker(
    activity: ComponentActivity,
    today: LocalDate,
    onPicked: (LocalDate) -> Unit
) {
    DatePickerDialog(
        activity,
        { _, y, m, d ->
            onPicked(LocalDate.of(y, m + 1, d))
        },
        today.year,
        today.monthValue - 1,
        today.dayOfMonth
    ).show()
}

fun pluralize(value: Int, singular: String, plural: String): String {
    return if (value == 1) singular else plural
}